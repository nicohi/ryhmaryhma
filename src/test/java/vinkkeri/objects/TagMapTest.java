package vinkkeri.objects;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TagMapTest {
    private TagMap tagMap;

    @Before
    public void setUp() {
        this.tagMap = new TagMap();
    }

    @Test
    public void domainYoutubeWorks() {
        final String cuteCatVideo = "https://www.youtube.com/watch?v=X9x7dVZms4M";
        List<String> found = tagMap.find(cuteCatVideo);
        assertTrue(found.contains("video"));
        assertFalse(found.contains("article"));
    }

    @Test
    public void domainAcmWorks() {
        final String algorithmArticle = "https://dl.acm.org/citation.cfm?id=2007097";
        List<String> found = tagMap.find(algorithmArticle);
        assertTrue(found.contains("article"));
        assertFalse(found.contains("video"));
    }

    @Test
    public void domainIeeeWorks() {
        final String networkArticle = "https://ieeexplore.ieee.org/document/7169508";
        List<String> found = tagMap.find(networkArticle);
        assertTrue(found.contains("article"));
        assertFalse(found.contains("video"));
    }
}
