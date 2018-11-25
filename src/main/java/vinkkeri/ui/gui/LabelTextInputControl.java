package vinkkeri.ui.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author jpssilve
 */
public class LabelTextInputControl {

    private Label label;
    private TextInputControl field;

    public LabelTextInputControl(String labelName, TextInputControl field, String promptText) {
        this.label = new Label(labelName);
        this.field = field;
        this.field.setPromptText(promptText);
    }

    public void clearField() {
        this.field.clear();
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setField(TextInputControl field) {
        this.field = field;
    }

    public Label getLabel() {
        return this.label;
    }

    public TextInputControl getField() {
        return this.field;
    }
}
