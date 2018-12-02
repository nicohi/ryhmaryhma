/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import vinkkeri.AppTest;
import vinkkeri.database.SQLiteTipDao;

import static org.testfx.api.FxAssert.verifyThat;

import vinkkeri.objects.Tip;

import java.io.File;

/**
 * @author Olli K. Kärki
 */

// tuhoon tän luokan jossain vaiheessa, käytetään pelkästään cucumberia gui testeihin
public class DisplayTest extends AppTest {

    private SQLiteTipDao tipDao;
    private final String TITLE_LABEL = "#titleLabel";
    private final String TITLE_FIELD = "#titleField";
    private final String AUTHOR_LABEL = "#authorLabel";
    private final String AUTHOR_FIELD = "#authorField";
    private final String URL_LABEL = "#urlLabel";
    private final String URL_FIELD = "#urlField";
    private final String ISBN_LABEL = "#isbnLabel";
    private final String ISBN_FIELD = "#isbnField";
    private final String COMMENT_LABEL = "#commentLabel";
    private final String COMMENT_AREA = "#commentArea";
    private final String TAGS_LABEL = "#tagsLabel";
    private final String TAGS_FIELD = "#tagField";
    private final String ADD_TIP_BUTTON = "#addTip";
    private final String FLIP_READ_BUTTON = "#flipRead";
    private final String DELETE_TIP_BUTTON = "#deleteTip";
    private final String ADD_BUTTON = "#addButton";
    private final String CLEAR_BUTTON = "#clearButton";
    private final String BACK_BUTTON = "#backButton";

    @Before
    public void init() {
        System.setProperty("use.test.db", "true");
        this.tipDao = new SQLiteTipDao("jdbc:sqlite:test.db");
    }

    // After each test the test Tip is removed
    @After
    public void tearDown() {
        tipDao.remove(tipDao.getNewestID());
    }

    @Test(expected = FxRobotException.class)
    public void clickOnBogusElement() {
        clickOn("#naamakalakukko");
    }

    @Test
    public void clearWorks() {
        clickOn(ADD_TIP_BUTTON);
        verifyThat(TITLE_LABEL, (Label label) -> {
            String text = label.getText();
            return text.equals("Title");
        });
        clickOn(TITLE_FIELD);
        verifyThat(TITLE_FIELD, (TextField textField) -> {
            String text = textField.getText();
            return text == null || text.equals("");
        });
        type("TESTTITLE");
        verifyThat(TITLE_FIELD, (TextField textField) -> {
            String text = textField.getText();
            return text.equals("testtitle");
        });
        clickOn(CLEAR_BUTTON);
        verifyThat(TITLE_FIELD, (TextField textField) -> {
            String text = textField.getText();
            return text == null || text.equals("");
        });
        clickOn(BACK_BUTTON);
        verifyThat(ADD_TIP_BUTTON, (Button button) -> {
            String text = button.getText();
            return text.equals("Add Tip");
        });
    }

    @Test
    public void createTipAndDeleteWorks() {
        clickOn(ADD_TIP_BUTTON);
        verifyThat(TITLE_LABEL, (Label label) -> {
            String text = label.getText();
            return text.equals("Title");
        });
        clickOn(TITLE_FIELD);
        verifyThat(TITLE_FIELD, (TextField textField) -> {
            String text = textField.getText();
            return text == null || text.equals("");
        });
        type("TESTTITLE");
        clickOn(ADD_BUTTON);
        clickOn(BACK_BUTTON);
        find("TESTTITLE".toLowerCase());
        clickOn("TESTTITLE".toLowerCase());
        clickOn(DELETE_TIP_BUTTON);

        // tää olettaa että listassa ei ole mitään
        verifyThat("#tipsList", (TableView tableview) -> {
            return tableview.getItems().isEmpty();
        });

        tipDao.remove(tipDao.getNewestID());
    }

    @Test
    public void flipButtonWorks() {
        clickOn(ADD_TIP_BUTTON);
        clickOn(TITLE_FIELD);
        type("TESTTITLE");
        clickOn(ADD_BUTTON);
        clickOn(BACK_BUTTON);
        find("TESTTITLE".toLowerCase());
        clickOn("TESTTITLE".toLowerCase());

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.isRead().equals("false");
        });

        clickOn(FLIP_READ_BUTTON);
        find("TESTTITLE".toLowerCase());
        clickOn("TESTTITLE".toLowerCase());

        //asdasdasd
        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return !tip.isRead().equals("false");
        });

        tipDao.remove(tipDao.getNewestID());
    }

    @Test
    public void allAddedFieldsAreShown() {
        clickOn(ADD_TIP_BUTTON);
        clickOn(TITLE_FIELD);
        type("TESTTITLE");
        clickOn(AUTHOR_FIELD);
        type("SOMEONE");
        clickOn(URL_FIELD);
        type("ADDRESS");
        clickOn(ISBN_FIELD);
        type("00500");
        clickOn(COMMENT_AREA);
        type("BEST");
        clickOn(TAGS_FIELD);
        type("LOREM");

        clickOn(ADD_BUTTON);
        clickOn(BACK_BUTTON);

        find("TESTTITLE".toLowerCase());
        clickOn("TESTTITLE".toLowerCase());

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getTitle().equals("testtitle");
        });

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getAuthor().equals("someone");
        });

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getUrl().equals("address");
        });

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getIsbn().equals("00500");
        });

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getSummary().equals("best");
        });

        verifyThat("#tipsList", (TableView tableview) -> {
            Tip tip = (Tip) tableview.getSelectionModel().getSelectedItem();
            return tip.getTags().contains("lorem");
        });

        tipDao.remove(tipDao.getNewestID());
    }

    @AfterClass
    public static void cleanUp() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
        System.setProperty("use.test.db", "false");
    }
}
