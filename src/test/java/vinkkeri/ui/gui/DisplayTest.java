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
import org.junit.Before;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobotException;
import vinkkeri.AppTest;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.objects.Tip;

/**
 *
 * @author Olli K. Kärki
 */
public class DisplayTest extends AppTest {

    SQLiteTipDao tipDao;

    @Before
    public void init() {
        this.tipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
    }

    @Test(expected = FxRobotException.class)
    public void clickOnBogusElement() {
        clickOn("#naamakalakukko");
    }

    @Test
    public void clearWorks() {
        clickOn("#addTip");
        verifyThat("#titleLabel", (Label label) -> {
            String text = label.getText();
            return text.equals("Title");
        });
        clickOn("#titleField");
        verifyThat("#titleField", (TextField textField) -> {
            String text = textField.getText();
            return text.equals("") || text == null;
        });
        type("TESTTITLE");
        verifyThat("#titleField", (TextField textField) -> {
            String text = textField.getText();
            return text.equals("testtitle");
        });
        clickOn("#clearButton");
        verifyThat("#titleField", (TextField textField) -> {
            String text = textField.getText();
            return text.equals("") || text == null;
        });
        clickOn("#backButton");
        verifyThat("Add Tip", (Button button) -> {
            String text = button.getText();
            return text.equals("Add Tip");
        });
    }

    @Test
    public void createTipAndDeleteWorks() {
        clickOn("#addTip");
        verifyThat("#titleLabel", (Label label) -> {
            String text = label.getText();
            return text.equals("Title");
        });
        clickOn("#titleField");
        verifyThat("#titleField", (TextField textField) -> {
            String text = textField.getText();
            return text.equals("") || text == null;
        });
        type("TESTTITLEFORTESTPURPOSETHATONLYEXISTSFORTESTPURPOSE");
        clickOn("#addButton");
        clickOn("#backButton");
        find("TESTTITLEFORTESTPURPOSETHATONLYEXISTSFORTESTPURPOSE".toLowerCase());
        clickOn("TESTTITLEFORTESTPURPOSETHATONLYEXISTSFORTESTPURPOSE".toLowerCase());
        int safetyId = tipDao.getNewestID();
        clickOn("Delete Tip");

        tipDao.remove(safetyId);
    }

}
