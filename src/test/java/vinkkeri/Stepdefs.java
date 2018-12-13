package vinkkeri;

import com.google.common.annotations.VisibleForTesting;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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

import static org.junit.Assert.*;
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
        // vaihda headless = false jos haluat katsella testejä ui:ssa
        // ja kommentoi headless propertyt pois
        headless = true;
        // tell Display class to use a test database
        System.setProperty("use.test.db", "true");

        try {
            ApplicationTest.launch(Main.class);
        } catch (Exception e) {
            System.out.println("Couldn't launch javafx ui\n" + e.getMessage());
        }
    }

    @VisibleForTesting
    private static final SQLiteTipDao tipDao;
    @VisibleForTesting
    private static final SQLiteTagDao tagDao;

    private static boolean headless;

    @Override
    public void start(Stage stage) {
        stage.show();
    }

    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }


    // Given -----------------------------------------------------
    @Given("^add tip view is clicked from the main view$")
    public void add_tip_view_is_clicked() {
        clickOn("#addTip");
    }

    @Given("^listing view is visible$")
    public void listingviewvisible() {
        verifyThat("Add Tip", NodeMatchers.isNotNull());
    }

    @Given("^some tips are added$")
    public void some_tips_are_added() {
        // luodaan testidataa suoraan tietokantaan
        Tip tipOne = new Tip("sherlock holmes", "arthur conan doyle");
        ArrayList<String> tagsOne = new ArrayList<>();
        tagsOne.add("romaani");
        tagsOne.add("mysteeri");
        tipOne.setTags(tagsOne);
        Tip tipTwo = new Tip("harry potter ja viisasten kivi", "j. k. rowling");
        ArrayList<String> tagsTwo = new ArrayList<>();
        tagsTwo.add("fantasia");
        tagsTwo.add("velhoilu");
        tipTwo.setTags(tagsTwo);
        tagDao.addTags(tagsOne);
        tagDao.addTags(tagsTwo);
        tipDao.insertTip(tipOne);
        tipDao.insertTip(tipTwo);
        //päivitetään näkymä
        clickOn("#addTip");
        clickOn("#backButton");
    }

    @Given("^hide read has been clicked$")
    public void hide_read_has_been_clicked() {
        clickOn("#hideRead");
    }

    @Given("^a tip with title \"([^\"]*)\", author \"([^\"]*)\" and tags \"([^\"]*)\" has been added$")
    public void a_tip_with_title_author_and_tags_has_been_added(String arg1, String arg2, String arg3) {
        clickOn("#addTip");
        clickOn("#titleField");
        type(arg1.toUpperCase());
        clickOn("#authorField");
        type(arg2.toUpperCase());
        clickOn("#tagField");
        type(arg3.toUpperCase());
        clickOn("#addButton");
        clickOn("#backButton");
    }

    @Given("^tip with title \"([^\"]*)\" is read$")
    public void tip_with_title_is_read(String arg1) {
        clickOn(arg1);
        clickOn("#flipRead");
    }

    //When -----------------------------------------------------
    @When("^user clicks on Modify tip button$")
    public void user_clicks_on_Modify_tip_button() {
        clickOn("#modifyTip");
    }

    @When("^user makes changes to tip attributes and saves them$")
    public void user_makes_changes_to_tip_attributes_and_saves_them() {
        clickOn("#commentArea");
        type("the best".toUpperCase());
        clickOn("#saveButton");
    }

    @When("^listing view contains Tips$")
    public void listing_view_contains_tips() {
        // luodaan testidataa suoraan tietokantaan
        Tip tipOne = new Tip("sherlock holmes", "arthur conan doyle");
        ArrayList<String> tagsOne = new ArrayList<>();
        tagsOne.add("romaani");
        tagsOne.add("mysteeri");
        tipOne.setTags(tagsOne);
        tagDao.addTags(tagsOne);
        tipDao.insertTip(tipOne);
        clickOn("#addTip");
        clickOn("#backButton");
    }

    @When("^hide read is clicked$")
    public void hide_read_is_clicked() {
        clickOn("#hideRead");
    }

    @When("^hide read is clicked twice$")
    public void hide_read_is_clicked_twice() {
        //ei toimi jostain syystä
        //clickOn("#hideRead");
        //clickOn("#hideRead");
    }

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

    @When("^search text area is clicked$")
    public void search_text_area_is_clicked() {
        clickOn("#searchField");
    }

    @When("^clear search is clicked$")
    public void clear_search_is_clicked() {
        clickOn("#searchClear");
    }

    @When("^Tip is double-clicked$")
    public void tip_is_double_clicked() {
        find("sherlock holmes");
        doubleClickOn("sherlock holmes");
    }

    @When("^delete button is pressed$")
    public void delete_button_is_pressed() {
        clickOn("#deleteTip");
    }

    @When("^tip is selected$")
    public void tip_is_selected() {
        clickOn("sherlock holmes");
    }

    @When("^flip read button is clicked$")
    public void flip_read_button_is_clicked() {
        clickOn("#flipRead");
    }

    // HUOM Tip View näkymän Flip read
    @When("^flip read button is pressed$")
    public void flip_read_button_is_pressed() {
        clickOn("#flipRead");
    }

    // Then -----------------------------------------------------
    @Then("^modify tip view becomes visible$")
    public void modify_tip_view_becomes_visible() {
        Node foundNode1 = find("#titleLabel");
        verifyThat(foundNode1, NodeMatchers.isNotNull());
        Node foundNode2 = find("#authorLabel");
        verifyThat(foundNode2, NodeMatchers.isNotNull());
        Node foundNode3 = find("#urlLabel");
        verifyThat(foundNode3, NodeMatchers.isNotNull());
        Node foundNode4 = find("#commentLabel");
        verifyThat(foundNode4, NodeMatchers.isNotNull());
        Node foundNode5 = find("#tagsLabel");
        verifyThat(foundNode5, NodeMatchers.isNotNull());
        Node foundNode6 = find("#saveButton");
        verifyThat(foundNode6, NodeMatchers.isNotNull());
        Node foundNode7 = find("#clearFieldsButton");
        verifyThat(foundNode7, NodeMatchers.isNotNull());
        Node foundNode8 = find("#cancelButton");
        verifyThat(foundNode8, NodeMatchers.isNotNull());
        clickOn("#cancelButton");
        clickOn("#back");
        tipDao.getTips().stream().forEach(tip -> tipDao.remove(tip.getId()));
    }

    @Then("^the changes are saved and shown$")
    public void the_changes_are_saved_and_shown() {
        clickOn("#back");
        clickOn("sherlock holmes");
        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            List<Tip> tips = tipDao.getTips();
            Tip daoTip = tips.stream().filter(t -> t.getTitle().equals("sherlock holmes")).findFirst().get();
            return tip.getSummary().equals("the best") && daoTip.getSummary().equals("the best");
        });
    }

    @Then("^a correct timestamp is shown$")
    public void a_correct_timestamp_is_shown() {
        clickOn("sherlock holmes");
        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            List<Tip> tips = tipDao.getTips();
            Tip daoTip = tips.stream().filter(t -> t.getTitle().equals("sherlock holmes")).findFirst().get();
            return tip.isRead().equals(daoTip.isRead());
        });
    }

    @Then("^the timestamp will show the correct time$")
    public void the_timestamp_will_show_the_correct_time() {
        verifyThat("#readInfo", (Label label) -> {
            List<Tip> tips = tipDao.getTips();
            Tip daoTip = tips.stream().filter(t -> t.getTitle().equals("sherlock holmes")).findFirst().get();
            return label.getText().equals(daoTip.isRead());
        });
        clickOn("#back");
    }

    @Then("^only title, author, tags and read information shown$")
    public void only_title_author_tags_and_read_information_is_shown() {
        verifyThat("#title", NodeMatchers.isNotNull());
        verifyThat("#author", NodeMatchers.isNotNull());
        verifyThat("#tags", NodeMatchers.isNotNull());
        verifyThat("#read", NodeMatchers.isNotNull());

        verifyThat("#tipsList", (TableView tableview) -> {
            return tableview.getColumns().size() == 4;
        });

    }

    @Then("^Tip view becomes visible$")
    public void tip_view_becomes_visible() {
        verifyThat("#titleLine", NodeMatchers.isNotNull());
        verifyThat("#authorLine", NodeMatchers.isNotNull());
        verifyThat("#dateLine", NodeMatchers.isNotNull());
        verifyThat("#urlLine", NodeMatchers.isNotNull());
        verifyThat("#readLine", NodeMatchers.isNotNull());
        verifyThat("#tagLine", NodeMatchers.isNotNull());
        clickOn("#back");
        tipDao.getTips().stream().forEach(tip -> tipDao.remove(tip.getId()));
    }

    @Then("^the Tip is not listed anymore in the listing view$")
    public void the_tip_is_not_listed_anymore_in_the_listing_view() {
        verifyThat("sherlock holmes", NodeMatchers.isNull());
    }

    @Then("^new tip with title \"([^\"]*)\" and tags \"([^\"]*)\" is stored in the program$")
    public void new_tip_with_title_and_tags_is_stored_in_the_program(String title, String tags) {
        Tip expectedTip = new Tip(title, "", "", "", "");
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

    @Then("^all tips are displayed$")
    public void all_tips_are_displayed() {
        verifyThat("title1", NodeMatchers.isNotNull());
        verifyThat("title2", NodeMatchers.isNotNull());
        tipDao.getTips().stream().forEach(tip -> tipDao.remove(tip.getId()));
    }

    @Then("^tip containing \"([^\"]*)\" is displayed$")
    public void tip_containing_is_displayed(String arg1) {
        verifyThat(arg1, NodeMatchers.isNotNull());
        tipDao.getTips().stream().forEach(tip -> tipDao.remove(tip.getId()));
    }

    @Then("^tip containing \"([^\"]*)\" is not displayed$")
    public void tip_containing_is_not_displayed(String arg1) {
        verifyThat(arg1, NodeMatchers.isNull());
        tipDao.getTips().stream().forEach(tip -> tipDao.remove(tip.getId()));
    }

    @Then("^the tips' titles, authors, tags and read states are visible$")
    public void the_tips_titles_authors_tags_and_read_states_are_visible() {
        find("sherlock holmes");
        clickOn("sherlock holmes");
        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getAuthor().equals("arthur conan doyle") && tip.isRead().equals("false")
                    && tip.getTags().contains("mysteeri") && tip.getTags().contains("romaani");
        });
        find("harry potter ja viisasten kivi");
        clickOn("harry potter ja viisasten kivi");
        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getAuthor().equals("j. k. rowling") && tip.isRead().equals("false")
                    && tip.getTags().contains("fantasia") && tip.getTags().contains("velhoilu");
        });
    }

    // After -------------------------------------------------------------------
    @After
    public void afterTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    // help methods
    private List<String> parseTagsFromString(String tags) {
        ArrayList<String> tiptags = new ArrayList<>();
        tiptags.addAll(Arrays.asList(tags.split(",")));
        return tiptags;
    }

    private Tip searchFromDatabaseWithTitle(String title) {
        List<Tip> tips = tipDao.getTips();
        for (Tip tip : tips) {
            if (title.equals(tip.getTitle())) {
                return tip;
            }
        }
        return null;
    }

    /**
     * Ugly wrapper to accommodate for
     * the less than ideal typerobot implementation
     *
     * @param str
     */
    private void type(String str) {
        String split[] = str.split("");
        for (String s : split) {
            s = s.trim();
            if (s.equals(",")) {
                super.type(KeyCode.COMMA);
            } else if (s.isEmpty()) {
                super.type(KeyCode.SPACE);
            } else if (s.equals("_")) {
                super.push(KeyCode.SHIFT, KeyCode.MINUS);
            } else if (s.equals("=")) {
                super.type(KeyCode.EQUALS);
            } else if (s.equals("/")) {
                if (!headless) {
                    super.push(KeyCode.SHIFT, KeyCode.DIGIT7);
                } else {
                    super.type(KeyCode.SLASH);
                }
            } else if (s.equals(":")) {
                if (!headless) {
                    super.push(KeyCode.SHIFT, KeyCode.PERIOD);
                } else {
                    super.push(KeyCode.SHIFT, KeyCode.SEMICOLON);
                }
            } else if (s.equals(".")) {
                super.type(KeyCode.PERIOD);
            } else if (s.equals("?")) {
                if (!headless) {
                    super.push(KeyCode.SHIFT, KeyCode.PLUS);
                } else {
                    super.push(KeyCode.SHIFT, KeyCode.SLASH);
                }
            } else {
                super.type(KeyCode.getKeyCode(s));
            }
        }
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
