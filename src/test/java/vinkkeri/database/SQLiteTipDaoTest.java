package vinkkeri.database;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import vinkkeri.objects.Tip;

/**
 *
 * @author jpssilve
 */
public class SQLiteTipDaoTest {

    SQLiteTipDao SQLiteTipDao;

    @Before
    public void setUp() {
        this.SQLiteTipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
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
    public void TipInsertAndDeleteWorks() {
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

        assertTrue(!tipTitles.contains("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project"));
    }

    @Test
    public void markingReadWorks() {
        this.SQLiteTipDao.insertTip(new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", false));

        List<Tip> tips = this.SQLiteTipDao.getTips();

        boolean corRead = false;

        for (Tip tip : tips) {
            if (tip.getTitle().equals("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project")) {
                if (tip.isRead() == false) {
                    corRead = true;
                    break;
                }
            }
        }

        assertTrue(corRead);

        this.SQLiteTipDao.markReadValue("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project", true);

        tips.clear();
        tips = this.SQLiteTipDao.getTips();

        corRead = false;

        for (Tip tip : tips) {
            if (tip.getTitle().equals("Introduction to Algorithms - that exists solely for the purpose of testing ryhmaryhma project")) {
                if (tip.isRead() == true) {
                    corRead = true;
                    break;
                }
            }
        }

        assertTrue(corRead);

        this.SQLiteTipDao.remove(this.SQLiteTipDao.getNewestID());
    }
}
