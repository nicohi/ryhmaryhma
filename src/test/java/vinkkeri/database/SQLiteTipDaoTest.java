package vinkkeri.database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import vinkkeri.objects.Tip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
        this.SQLiteTipDao = new SQLiteTipDao("jdbc:sqlite:test.db");
        this.tagDao = new SQLiteTagDao("jdbc:sqlite:test.db");
        this.courseDao = new SQLiteCourseDao("jdbc:sqlite:test.db");
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
    public void tipInsertAndDeleteWorks() {
        this.SQLiteTipDao.insertTip(new Tip(3, "2018-11-15", "Introduction to Algorithms - that exists solely", "CLRS", "algos", "http://mitpress.mit.edu", ""));

        List<String> tipTitles = new ArrayList<>();
        List<Tip> tips = this.SQLiteTipDao.getTips();

        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        assertTrue(tipTitles.contains("Introduction to Algorithms - that exists solely"));

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
        this.SQLiteTipDao.insertTip(new Tip(3, "2018-11-15", "Introduction to Algorithms - that exists", "CLRS", "algos", "http://mitpress.mit.edu", ""));

        List<Tip> tips = this.SQLiteTipDao.getTips();

        boolean corRead = false;

        String error = "";

        for (Tip tip : tips) {
            if (tip.getTitle().equals("Introduction to Algorithms - that exists")) {
                this.toBeDeletedTestTipsIds.add(tip.getId());
                if (tip.isRead().equals("false") || tip.isRead().equals("")) {
                    corRead = true;
                    break;
                } else {
                    error = error + " - " + tip.isRead() + "; ";
                }
            }
        }

        assertTrue(error, corRead);

        this.SQLiteTipDao.markReadValue(this.SQLiteTipDao.getNewestID(), "notfalse");

        tips.clear();
        tips = this.SQLiteTipDao.getTips();

        corRead = false;

        for (Tip tip : tips) {
            if (tip.getTitle().equals("Introduction to Algorithms - that exists")
                    && !tip.isRead().equals("false")) {
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

        Tip readingTip = new Tip("CS courses -- for testing", "", "kapistely", "https://www.cs.helsinki.fi/courses/", "");
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

        Tip readingTip = new Tip("Testing -- CS courses", "", "kapistely", "https://www.cs.helsinki.fi/courses/", "");
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

        Tip readingTip = new Tip("Testing more -- CS courses", "", "kapistely", "https://www.cs.helsinki.fi/courses/", "");
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


    @Test
    public void updateTipWorks() {

        Tip readingTip = new Tip("Testing more CS cour", "", "kapiely", "https://www.cs./courses/", "");
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        readingTip.setTags(tags);

        this.tagDao.addTags(tags);
        this.SQLiteTipDao.insertTip(readingTip);

        Tip tip = this.SQLiteTipDao.getTips().get(0);
        this.toBeDeletedTestTipsIds.add(tip.getId());

        assertTrue(tip.getTitle().equals("Testing more CS cour"));
        assertTrue(tip.getUrl().equals("https://www.cs./courses/"));


        for (String tag : tip.getTags()) {
            assertTrue(tags.contains(tag));
        }

        assertTrue(tip.getTags().size() == 2);

        List<String> tags2 = new ArrayList<>();
        tags2.add("tag3");
        tags2.add("tag4");

        Tip readingTip2 = new Tip("Testing more CSa cour", "a", "kapiealy", "https://www.cs./couarses/", "a");
        List<String> tags3 = new ArrayList<>();
        tags3.add("tag2");
        tags3.add("tag3");
        readingTip2.setTags(tags3);

        this.tagDao.addTags(tags3);
        this.SQLiteTipDao.insertTip(readingTip2);
        this.toBeDeletedTestTipsIds.add(this.SQLiteTipDao.getNewestID());

        tip.recreate(tip.getDate(), "new title test", tip.getAuthor(), "hahaha", tip.getUrl(), tip.isRead(), tags2);

        assertTrue(tip.getTitle().equals("new title test"));

        this.tagDao.addTags(tags2);
        this.SQLiteTipDao.updateTip(tip);
        this.tagDao.removeTags(tags);

        List<Tip> tips = this.SQLiteTipDao.getTips();

        assertTrue(tips.size() == 2);

        tip = tips.get(0);
        assertNotNull("tip from db was null", tip);
        this.toBeDeletedTestTipsIds.add(tip.getId());

        assertTrue(tip.getUrl().equals("https://www.cs./courses/"));
        for (String tag : tip.getTags()) {
            assertFalse(tags.contains(tag));
        }

        assertTrue(tip.getTags().contains("tag3"));
        assertTrue(tip.getTags().contains("tag4"));
        assertTrue(tip.getTags().size() == 2);

        assertTrue(tip.getTitle().equals("new title test"));

        List<String> tagsList = this.tagDao.getTags();
        assertFalse(tagsList.contains("tag1"));
        assertTrue(tagsList.contains("tag2"));
        assertTrue(tagsList.contains("tag3"));
        assertTrue(tagsList.contains("tag4"));
        assertTrue(tagsList.size() == 3);
    }

    @AfterClass
    public static void deleteTestDatabase() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
        System.setProperty("use.test.db", "false");
    }

}
