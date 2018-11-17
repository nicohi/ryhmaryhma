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
        for (int i = 0; i < this.tipDao.getTips().size(); i++) {
            tipTitles.add(this.tipDao.getTips().get(i).getTitle());
        }

        assertTrue(tipTitles.contains("Jukka Palmun Tuho"));
        assertTrue(tipTitles.contains("Jukka Palmun Kosto"));
        assertTrue(tipTitles.contains("Jukka Palmun Seikkailut"));
    }

    // No Insertion tests yet since no deletion method, and hence database gets cluttered with the same entry everytime the test is run
    @After
    public void tearDown() {
    }

}
