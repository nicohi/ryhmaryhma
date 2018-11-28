/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobotException;
import vinkkeri.AppTest;

/**
 *
 * @author Olli K. Kärki
 */
public class DisplayTest extends AppTest {
    
    @Test(expected = FxRobotException.class)
    public void clickOnBogusElement() {
        clickOn("#naamakalakukko");
    }
    
    @Test
    public void changeViewToAddAndBack() {
        clickOn("Add Tip");
        verifyThat("#titleLabel", (Label label) -> {
            String text = label.getText();
            return text.equals("Title");
        });
        clickOn("Back");
        verifyThat("Add Tip", (Button button) -> {
            String text = button.getText();
            return text.equals("Add Tip");
        });
    }
    
    @Test
    public void clearWorks() {
        clickOn("Add Tip");
        verifyThat("#titleLabel", (Label label) -> {
            String text = label.getText();
            return text.equals("Title");
        });
        clickOn("#titleField");
        verifyThat("#titleField", (TextField textField) -> {
            String text = textField.getText();
            return text.equals("") || text == null;
        });
    }
    
}
