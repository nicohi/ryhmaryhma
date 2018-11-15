package vinkkeri.objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jpssilve
 */
public class TipTest {

    private Tip tip;

    @Before
    public void setUp() {
        this.tip = new Tip(3, "15/11/2018", "Book", "Introduction to Algorithms", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", true);
    }

    // Tests below test the get methods of the Tip class, excluding the methods that return lists (no add methods for lists yet)
    @Test
    public void getIdTest1() {
        assertEquals(3, this.tip.getId());
    }

    // Replace test value with the correct sql formatting
    @Test
    public void getDateTest1() {
        assertEquals("15/11/2018", this.tip.getDate());
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

    @Test
    public void getTagsTest1() {

    }

    @Test
    public void getRelCTest1() {

    }

    @Test
    public void getReqCTest1() {

    }
}
