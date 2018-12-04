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
 * @author jpssilve
 */
public class TipTest {

    private Tip tip;

    @Before
    public void setUp() {
        this.tip = new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", "notfalse");
    }

    @Test
    public void toStringTest() {
        String tostring = tip.toString();
        String expected = "Title: " + "Introduction to Algorithms";
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
        assertFalse(tip.isRead().equals("false"));
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
        this.tip.setRelatedCourses(relCourses);

        assertTrue(this.tip.getRelatedCourses().contains("Tietokantasovellus"));
        assertTrue(this.tip.getRelatedCourses().contains("Tietokannan suunnittelu"));
        assertTrue(this.tip.getRelatedCourses().contains("Tietokantojen perusteet"));
    }

    @Test
    public void settingRequiredCoursesWorks1() {
        List<String> required = new ArrayList<>();
        required.add("Introduction to Artificial Intelligence");
        required.add("Introduction to Machine Learning");
        required.add("Computational Statistics");
        this.tip.setRequiredCourses(required);

        assertTrue(this.tip.getRequiredCourses().contains("Computational Statistics"));
        assertTrue(this.tip.getRequiredCourses().contains("Introduction to Artificial Intelligence"));
        assertTrue(this.tip.getRequiredCourses().contains("Introduction to Machine Learning"));
        assertFalse(this.tip.getRequiredCourses().contains("Randomized Algorithms"));
    }

    @Test
    public void equalsWorks() {
        assertFalse(tip.equals("one helluva object"));
        assertFalse(tip.equals(new Tip("title", "author")));
        Tip similarTip = new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms part two", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", "notfalse");
        assertFalse(tip.equals(similarTip));
        similarTip = new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", "a value");
        assertFalse(tip.equals(similarTip));
    }
}
