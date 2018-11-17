package vinkkeri.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jpssilve
 */
public class TagDaoTest {

    private TagDao tagDao;

    @Before
    public void setUp() {
        this.tagDao = new TagDao("jdbc:sqlite:database.db");
    }

    @Test
    public void gettingTagsWorks1() throws SQLException {
        List<String> tags = this.tagDao.getTags();

        assertTrue(tags.contains("Fiktio"));
        assertTrue(tags.contains("Fantasia"));
        assertTrue(tags.contains("Historia"));
    }

    @Test
    public void addingTagsWorks1() throws SQLException {
        List<String> tags = new ArrayList<>();
        tags.add("Theoretical computer science");
        this.tagDao.addTags(tags);

        assertTrue(this.tagDao.getTags().contains("Theoretical computer science"));
    }

    @Test
    public void addingTagsWorks2() throws SQLException {
        List<String> tags = new ArrayList<>();
        tags.add("blog");
        tags.add("video");
        tags.add("lecture");
        tags.add("seminar");
        this.tagDao.addTags(tags);

        List<String> foundTags = this.tagDao.getTags();
        assertTrue(foundTags.contains("seminar"));
        assertTrue(foundTags.contains("video"));
        assertTrue(foundTags.contains("blog"));
        assertTrue(foundTags.contains("lecture"));
    }
}
