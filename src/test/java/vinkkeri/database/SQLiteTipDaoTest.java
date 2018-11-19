package vinkkeri.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    public void setUp() throws IOException {
        this.SQLiteTipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
    }

    @Test
    public void gettingTipsWorks1() throws SQLException, Exception {
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
    public void TipInsertAndDeleteWorks() throws SQLException, Exception {
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
}
