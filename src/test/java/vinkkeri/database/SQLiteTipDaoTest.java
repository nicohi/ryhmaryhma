package vinkkeri.database;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vinkkeri.objects.Tip;

/**
 * @author jpssilve
 */
public class SQLiteTipDaoTest {

    private SQLiteTipDao SQLiteTipDao;
    private SQLiteTagDao tagDao;
    private SQLiteCourseDao courseDao;
    private ArrayList<Integer> toBeDeletedTestTipsIds;

    @Before
    public void setUp() {
        this.SQLiteTipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
        this.tagDao = new SQLiteTagDao("jdbc:sqlite:database.db");
        this.courseDao = new SQLiteCourseDao("jdbc:sqlite:database.db");
        this.toBeDeletedTestTipsIds = new ArrayList<>();
    }

    // Make sure test Tips are deleted
    @After
    public void tearDown() {
        for (int i = 0; i < this.toBeDeletedTestTipsIds.size(); i++) {
            this.SQLiteTipDao.remove(this.toBeDeletedTestTipsIds.get(i));
        }
    }

    @Test
    public void gettingTipsWorks1() {
        List<String> tipTitles = new ArrayList<>();
        List<Tip> tips = this.SQLiteTipDao.getTips();
        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        assertTrue(tipTitles.contains("Jukka Palmun Tuho"));
        assertTrue(tipTitles.contains("Jukka Palmun Kosto"));
        assertTrue(tipTitles.contains("Jukka Palmun Seikkailut"));
    }

    @Test
    public void tipInsertAndDeleteWorks() {
        this.SQLiteTipDao.insertTip(new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", true));

        List<String> tipTitles = new ArrayList<>();
        List<Tip> tips = this.SQLiteTipDao.getTips();

        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        assertTrue(tipTitles.contains("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project"));

        this.SQLiteTipDao.remove(this.SQLiteTipDao.getNewestID());

        tipTitles.clear();
        tips = this.SQLiteTipDao.getTips();

        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        assertFalse(tipTitles.contains("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project"));
    }

    @Test
    public void markingReadWorks() {
        this.SQLiteTipDao.insertTip(new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", false));

        List<Tip> tips = this.SQLiteTipDao.getTips();

        boolean corRead = false;

        for (Tip tip : tips) {
            if (tip.getTitle().equals("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project")) {
                this.toBeDeletedTestTipsIds.add(tip.getId());
                if (!tip.isRead()) {
                    corRead = true;
                    break;
                }
            }
        }

        assertTrue(corRead);

        this.SQLiteTipDao.markReadValue(this.SQLiteTipDao.getNewestID(), true);

        tips.clear();
        tips = this.SQLiteTipDao.getTips();

        corRead = false;

        for (Tip tip : tips) {
            if (tip.getTitle().equals("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project")
                    && tip.isRead()) {
                corRead = true;
                break;
            }
        }

        assertTrue(corRead);

        this.SQLiteTipDao.remove(this.SQLiteTipDao.getNewestID());
    }

    @Test
    public void insertAddsRelatedTags() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("keskeinen");
        tags.add("webdevaus");

        Tip readingTip = new Tip("Link", "CS courses -- for testing", "", "kapistely", "", "https://www.cs.helsinki.fi/courses/", false);
        readingTip.setTags(tags);

        this.tagDao.addTags(tags);
        this.SQLiteTipDao.insertTip(readingTip);
        List<Tip> tips = this.SQLiteTipDao.getTips();

        boolean notFound = true;
        for (Tip tip : tips) {
            if (tip.getTitle().equals("CS courses -- for testing")) {
                this.toBeDeletedTestTipsIds.add(tip.getId());
                notFound = false;
                assertTrue(tip.getTags().contains("keskeinen"));
                assertTrue(tip.getTags().contains("webdevaus"));
                break;
            }
        }

        this.SQLiteTipDao.remove(this.SQLiteTipDao.getNewestID());

        if (notFound) {
            fail("Not added");
        }
    }

    @Test
    public void insertAddsRelatedCourses() {
        ArrayList<String> related = new ArrayList<>();
        related.add("TIRA");
        related.add("OHTU");

        Tip readingTip = new Tip("Link", "Testing -- CS courses", "", "kapistely", "", "https://www.cs.helsinki.fi/courses/", false);
        readingTip.setRelatedCourses(related);

        this.courseDao.addCourses(related);
        this.SQLiteTipDao.insertTip(readingTip);
        List<Tip> tips = this.SQLiteTipDao.getTips();

        boolean notFound = true;
        for (Tip tip : tips) {
            if (tip.getTitle().equals("Testing -- CS courses")) {
                this.toBeDeletedTestTipsIds.add(tip.getId());
                notFound = false;
                assertTrue(tip.getRelatedCourses().contains("TIRA"));
                assertTrue(tip.getRelatedCourses().contains("OHTU"));
                break;
            }
        }

        this.SQLiteTipDao.remove(this.SQLiteTipDao.getNewestID());

        if (notFound) {
            fail("Not added");
        }
    }

    @Test
    public void insertAddsRequiredCourses() {
        ArrayList<String> required = new ArrayList<>();
        required.add("TiKaPe");
        required.add("OHTU");

        Tip readingTip = new Tip("Link", "Testing more -- CS courses", "", "kapistely", "", "https://www.cs.helsinki.fi/courses/", false);
        readingTip.setRequiredCourses(required);

        this.courseDao.addCourses(required);
        this.SQLiteTipDao.insertTip(readingTip);
        List<Tip> tips = this.SQLiteTipDao.getTips();

        boolean notFound = true;
        for (Tip tip : tips) {
            if (tip.getTitle().equals("Testing more -- CS courses")) {
                this.toBeDeletedTestTipsIds.add(tip.getId());
                notFound = false;
                assertTrue(tip.getRequiredCourses().contains("TiKaPe"));
                assertTrue(tip.getRequiredCourses().contains("OHTU"));
                break;
            }
        }

        this.SQLiteTipDao.remove(this.SQLiteTipDao.getNewestID());

        if (notFound) {
            fail("Not added");
        }
    }
}
