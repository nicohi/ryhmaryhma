package vinkkeri.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import vinkkeri.objects.Tip;

/**
 *
 * @author jpssilve
 */
public class TipDaoTest {

    TipDao tipDao;

    @Before
    public void setUp() throws IOException {
        this.tipDao = new TipDao("jdbc:sqlite:database.db");
    }

    @Test
    public void gettingTipsWorks1() throws SQLException, Exception {
        List<String> tipTitles = new ArrayList<>();
        List<Tip> tips = this.tipDao.getTips();
        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        assertTrue(tipTitles.contains("Jukka Palmun Tuho"));
        assertTrue(tipTitles.contains("Jukka Palmun Kosto"));
        assertTrue(tipTitles.contains("Jukka Palmun Seikkailut"));
    }

    @Test
    public void insertingTipsWorks() throws SQLException, Exception {
        this.tipDao.insertTip(new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", true));

        List<String> tipTitles = new ArrayList<>();
        List<Tip> tips = this.tipDao.getTips();
        for (int i = 0; i < tips.size(); i++) {
            tipTitles.add(tips.get(i).getTitle());
        }

        assertTrue(tipTitles.contains("Introduction to Algorithms"));
    }
}
