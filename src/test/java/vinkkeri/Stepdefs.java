package vinkkeri;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.main.Main;
import vinkkeri.objects.Tip;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class Stepdefs extends ApplicationTest {

    static {
        // make sure tests are done with a clean database
        File testdb = new File("test.db");
        if (testdb.exists()) {
            testdb.delete();
        }
        // init daos
        final String testDatabaseAddress = "jdbc:sqlite:test.db";
        tagDao = new SQLiteTagDao(testDatabaseAddress);
        tipDao = new SQLiteTipDao(testDatabaseAddress);
        // properties for testfx
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
        // tell Display class to use a test database
        System.setProperty("use.test.db", "true");

        try {
            ApplicationTest.launch(Main.class);
        } catch (Exception e) {
            System.out.println("Couldn't launch javafx ui\n" + e.getMessage());
        }
    }

    static SQLiteTipDao tipDao;
    static SQLiteTagDao tagDao;

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

    public void type(String str) {
        String split[] = str.split("");
        for (String s : split) {
            // täs hyvää purkkaa koska toi getkeycode() ei toimi kunnolla
            s = s.trim();
            if (s.equals(",")) {
                super.type(KeyCode.COMMA);
            } else if (s.isEmpty()) {
                super.type(KeyCode.SPACE);
            } else {
                super.type(KeyCode.getKeyCode(s));
            }
        }
    }

    // Given -----------------------------------------------------

    @Given("^add tip view is clicked from the main view$")
    public void add_tip_view_is_clicked() {
        clickOn("#addTip");
    }

    @Given("^listing view is visible$")
    public void listingviewvisible() {
        verifyThat("Flip Read", NodeMatchers.isNotNull());
    }

    //When -----------------------------------------------------
    @When("^add tip button is clicked$")
    public void givenaddtipbuttonisclicked() {
        clickOn("#addTip");
    }

    @When("title text area is clicked$")
    public void titletextareaisclicked() {
        clickOn("#titleField");
    }

    @When("url text area is clicked$")
    public void urltextareaisclicked() {
        clickOn("#urlField");
    }

    @When("tag text area is clicked$")
    public void tagtextareaisclicked() {
        clickOn("#tagField");
    }

    @When("^author text area is clicked$")
    public void author_text_area_is_clicked() {
        clickOn("#authorField");
    }

    @When("^comment text area is clicked$")
    public void comment_text_area_is_clicked() {
        clickOn("#commentArea");
    }

    @When("^text \"([^\"]*)\" is entered$")
    public void textisentered(String text) {
        type(text.toUpperCase());
    }

    @When("^add button is clicked$")
    public void addbuttonisclicked() {
        clickOn("#addButton");
    }

    @When("^back button is clicked$")
    public void backbuttonisclicked() {
        clickOn("#backButton");
    }

    @When("^text \"([^\"]*)\" is clicked$")
    public void textbuttonisclicked(String text) {
        clickOn(text);
    }

    @When("^delete is clicked$")
    public void deleteisclicked() {
        clickOn("#deleteTip");
    }

    // Then -----------------------------------------------------

    @Then("^new tip with title \"([^\"]*)\" and tags \"([^\"]*)\" is stored in the program$")
    public void new_tip_with_title_and_tags_is_stored_in_the_program(String title, String tags) throws Throwable {
        Tip expectedTip = new Tip("book", title, "", "", "", "", "");
        List<String> tagList = parseTagsFromString(tags);
        expectedTip.setTags(tagList);

        Tip foundTip = searchFromDatabaseWithTitle(title);

        assertNotNull(foundTip);
        for (String tag : tags.split(",")) {
            assertTrue(foundTip.getTags().contains(tag));
        }
        removeNewestFromDatabase();
    }

    @Then("add view is visible")
    public void add_view_is_visible() {
        Node foundNode = find("#titleLabel");
        verifyThat(foundNode, NodeMatchers.isNotNull());
        clickOn("#backButton");
    }

    @Then("^new tip with title \"([^\"]*)\" and url \"([^\"]*)\" is stored in the program$")
    public void new_tip_with_title_and_url_is_stored_in_the_program(String title, String url) {
        Tip foundTip = searchFromDatabaseWithTitle(title);
        assertNotNull(foundTip);
        assertEquals(url, foundTip.getUrl());
        removeNewestFromDatabase();
    }

    @Then("^new tip with title \"([^\"]*)\" and author \"([^\"]*)\" and description \"([^\"]*)\" is stored in the program$")
    public void new_tip_with_title_and_author_and_description_is_stored_in_the_program(String title, String author, String comment) {
        Tip foundTip = searchFromDatabaseWithTitle(title.trim());
        tipDao.getTips().forEach(t -> System.out.println(t.getTitle()));
        assertNotNull("Tip with title \"" + title + "\" was not in the database", foundTip);
        assertEquals(title, foundTip.getTitle().trim());
        assertEquals(author, foundTip.getAuthor().trim());
        assertEquals(comment, foundTip.getSummary().trim());
        removeNewestFromDatabase();
    }


    @Then("^a tip with title \"([^\"]*)\" is not stored in the program$")
    public void tip_with_title_is_not_stored(String title) {
        verifyThat(title, NodeMatchers.isNull());
    }

    // After -------------------------------------------------------------------
    @After
    public void afterTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    // help methods
    public List<String> parseTagsFromString(String tags) {
        ArrayList<String> tiptags = new ArrayList<>();
        tiptags.addAll(Arrays.asList(tags.split(",")));
        return tiptags;
    }

    public Tip searchFromDatabaseWithTitle(String title) {
        List<Tip> tips = tipDao.getTips();
        for (Tip tip : tips) {
            if (title.equals(tip.getTitle())) {
                return tip;
            }
        }
        return null;
    }

    private void removeNewestFromDatabase() {
        tipDao.remove(tipDao.getNewestID());
    }

    @AfterClass
    public static void deleteTestDatabase() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
        System.setProperty("use.test.db", "false");
    }

}
