package vinkkeri;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.main.Main;
import vinkkeri.objects.Tip;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class Stepdefs extends ApplicationTest {

    static {
        tagDao = new SQLiteTagDao("jdbc:sqlite:database.db");
        tipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");

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
            super.type(KeyCode.getKeyCode(s));
        }
    }

    // Given -----------------------------------------------------
    @Given("^add tip is clicked$")
    public void givenaddtipisclicked() {
        clickOn("#addTip");
    }

    @Given("^listing view is visible$")
    public void listingviewvisible() {

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
    @Then("^new tip with title \"([^\"]*)\" and url \"([^\"]*)\" and tags \"([^\"]*)\" is stored in the program$")
    public void newtipwithtitleisstored(String title, String url, String tags) {
        List<Tip> tips = tipDao.getTips();
        boolean exists = false;
        Tip safety = null;
        for (Tip tip : tips) {
            if (tip.getTitle().equals(title)) {
                safety = tip;
                exists = true;
                break;
            }
        }
        if (exists) {
            tipDao.remove(tipDao.getNewestID());
        }
        assertTrue(exists);
        assertTrue(safety.getUrl().equals(url));
        for (String tag : tags.split(",")) {
            assertTrue(safety.getTags().contains(tag));
        }
        clickOn("#backButton");
    }

    @Then("add view is visible")
    public void addviewisvisible() {
        find("#titleField");
        assertTrue(true);
        clickOn("#backButton");
    }

    @Then("^new tip with title \"([^\"]*)\" and url \"([^\"]*)\" is stored in the program$")
    public void new_tip_with_title_and_url_is_stored_in_the_program(String title, String url) {
        // Write code here that turns the phrase above into concrete actions
        List<Tip> tips = tipDao.getTips();
        Tip tip = null;
        for (Tip t : tips) {
            if (t.getTitle().equals(title) && t.getUrl().equals(url)) {
                tip = t;
                break;
            }
        }
        assertNotNull(tip);
        tipDao.remove(tipDao.getNewestID());
    }


    @Then("^a tip with title \"([^\"]*)\" is not stored in the program$")
    public void tipwithtitleisnotstored(String title) {
        verifyThat(title, NodeMatchers.isNull());
    }

    // After -------------------------------------------------------------------
    @After
    public void afterTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

}
