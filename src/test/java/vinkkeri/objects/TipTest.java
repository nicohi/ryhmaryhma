package vinkkeri.objects;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author jpssilve
 */
public class TipTest {

    private Tip tip;

    @Before
    public void setUp() {
        this.tip = new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", true);
    }
    
    @Test
    public void toStringTest() {
        String tostring = tip.toString();
        String expected = "Title: " + "Introduction to Algorithms" + "\n"
                + "Added: " + "2018-11-15" + "\n"
                + "Author: " + "CLRS" + "\n"
                + "Summary: " + "algos" + "\n"
                + "ISBN: " + "9780262033848" + "\n"
                + "Link: " + "http://mitpress.mit.edu" + "\n"
                + "Read: " + true + "\n";
        assertTrue(tostring.equals(expected));
    }

    // Tests below test the get methods of the Tip class
    @Test
    public void getIdTest1() {
        assertEquals(3, this.tip.getId());
    }

    @Test
    public void getDateTest1() {
        assertEquals("2018-11-15", this.tip.getDate());
    }

    @Test
    public void getTypeTest1() {
        assertEquals("Book", this.tip.getType());
    }

    @Test
    public void getTitleTest1() {
        assertEquals("Introduction to Algorithms", this.tip.getTitle());
    }

    @Test
    public void getAuthorTest1() {
        assertEquals("CLRS", this.tip.getAuthor());
    }

    @Test
    public void getSummaryTest1() {
        assertEquals("algos", this.tip.getSummary());
    }

    @Test
    public void getIsbnTest1() {
        assertEquals("9780262033848", this.tip.getIsbn());
    }

    @Test
    public void getUrlTest1() {
        assertEquals("http://mitpress.mit.edu", this.tip.getUrl());
    }

    @Test
    public void isReadTest1() {
        assertTrue(this.tip.isRead());
    }

    // Tests below test the methods that involve lists
    @Test
    public void settingTagsWorks1() {
        List<String> tags = new ArrayList<>();
        tags.add("keycourse");
        tags.add("nicetoknow");
        tags.add("blaablaa");
        this.tip.setTags(tags);

        assertTrue(this.tip.getTags().contains("nicetoknow"));
        assertTrue(this.tip.getTags().contains("blaablaa"));
        assertTrue(this.tip.getTags().contains("keycourse"));
    }
    
    @Test
    public void settingRelatedCoursesWorks1() {
        List<String> relCourses = new ArrayList<>();
        relCourses.add("Tietokantojen perusteet");
        relCourses.add("Tietokantasovellus");
        relCourses.add("Tietokannan suunnittelu");
        this.tip.setRelC(relCourses);

        assertTrue(this.tip.getRelC().contains("Tietokantasovellus"));
        assertTrue(this.tip.getRelC().contains("Tietokannan suunnittelu"));
        assertTrue(this.tip.getRelC().contains("Tietokantojen perusteet"));
    }

    @Test
    public void settingRequiredCoursesWorks1() {
        List<String> required = new ArrayList<>();
        required.add("Introduction to Artificial Intelligence");
        required.add("Introduction to Machine Learning");
        required.add("Computational Statistics");
        this.tip.setReqC(required);

        assertTrue(this.tip.getReqC().contains("Computational Statistics"));
        assertTrue(this.tip.getReqC().contains("Introduction to Artificial Intelligence"));
        assertTrue(this.tip.getReqC().contains("Introduction to Machine Learning"));
        assertFalse(this.tip.getReqC().contains("Randomized Algorithms"));
    }
}
