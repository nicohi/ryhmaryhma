package vinkkeri.ui.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddTipController implements Initializable {

    // buttons
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button clearButton;

    // input fields
    ArrayList<TextInputControl> inputs;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField tagField;
    @FXML
    private TextArea commentArea;


    /**
     * init function that is called when the view is created.
     * Calls helper methods that create eventhandlers for ui elements.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inputs = new ArrayList<>();
        fillInputs();
        addButton();
        clearButton();
    }

    /**
     * Create an iterable list of inputfields.
     */
    private void fillInputs() {
        this.inputs.add(titleField);
        this.inputs.add(authorField);
        this.inputs.add(isbnField);
        this.inputs.add(tagField);
        this.inputs.add(commentArea);
    }


    public void addButton() {
        addButton.setOnAction(event -> {
            //validate fields


        });
    }

    private void clearFields() {
        for (TextInputControl tic : inputs) {
            tic.clear();
        }
    }

    private void clearButton() {
        this.clearButton.setOnAction(event -> {
            clearFields();
        });
    }

    private void createTip() {

    }
}
