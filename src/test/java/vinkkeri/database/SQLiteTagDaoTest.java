package vinkkeri.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author jpssilve
 */
public class SQLiteTagDaoTest {

    private SQLiteTagDao SQLiteTagDao;

    @Before
    public void setUp() {
        this.SQLiteTagDao = new SQLiteTagDao("jdbc:sqlite:test.db");
    }

    // Make sure test tags are removed by trying again
    @After
    public void tearDown() {
        List<String> tags = new ArrayList<>();
        tags.add("testing--code");
        tags.add("testing--software");
        tags.add("testing--hardware");
        this.SQLiteTagDao.removeTags(tags);
    }

    // FIXME: dependaa populoidusta tietokannasta
    @Test
    public void gettingTagsWorks1() {
        //FIXME
    }

    @Test
    public void addingTagsWorks1() {
        List<String> tags = new ArrayList<>();
        tags.add("Theoretical computer science");
        this.SQLiteTagDao.addTags(tags);

        assertTrue(this.SQLiteTagDao.getTags().contains("Theoretical computer science"));
    }

    @Test
    public void addingTagsWorks2() {
        List<String> tags = new ArrayList<>();
        tags.add("blog");
        tags.add("video");
        tags.add("lecture");
        tags.add("seminar");
        this.SQLiteTagDao.addTags(tags);

        List<String> foundTags = this.SQLiteTagDao.getTags();
        assertTrue(foundTags.contains("seminar"));
        assertTrue(foundTags.contains("video"));
        assertTrue(foundTags.contains("blog"));
        assertTrue(foundTags.contains("lecture"));
    }

    @Test
    public void removingExistingTagsWorks1() {
        List<String> tags = new ArrayList<>();
        tags.add("testing--code");
        tags.add("testing--software");
        tags.add("testing--hardware");
        this.SQLiteTagDao.addTags(tags);
        List<String> tagsWithAdded = this.SQLiteTagDao.getTags();
        assertTrue(tagsWithAdded.contains("testing--code"));
        assertTrue(tagsWithAdded.contains("testing--software"));
        assertTrue(tagsWithAdded.contains("testing--hardware"));

        this.SQLiteTagDao.removeTags(tags);
        List<String> tagsAfterRemoval = this.SQLiteTagDao.getTags();
        assertFalse(tagsAfterRemoval.contains("testing--code"));
        assertFalse(tagsAfterRemoval.contains("testing--software"));
        assertFalse(tagsAfterRemoval.contains("testing--hardware"));
    }

    @Test
    public void removingNonExistingTagsDoesNothing() {
        List<String> tagsOriginal = this.SQLiteTagDao.getTags();
        Collections.sort(tagsOriginal);

        List<String> nonExistingtagsToBeRemoved = new ArrayList<>();
        nonExistingtagsToBeRemoved.add("testing--does not exist 1");
        nonExistingtagsToBeRemoved.add("testing--does not exist 2");
        nonExistingtagsToBeRemoved.add("testing--does not exist 3");
        this.SQLiteTagDao.removeTags(nonExistingtagsToBeRemoved);

        List<String> tagsAfter = this.SQLiteTagDao.getTags();
        Collections.sort(tagsAfter);
        if (tagsOriginal.size() == tagsAfter.size()) {
            for (int i = 0; i < 0; i++) {
                assertEquals(tagsOriginal.get(i), tagsAfter.get(i));
            }
        } else {
            fail("Lists differ in length");
        }
    }
}
