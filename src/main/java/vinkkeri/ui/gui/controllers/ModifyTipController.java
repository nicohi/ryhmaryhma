package vinkkeri.ui.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Controller;
import vinkkeri.ui.gui.Display;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author jpssilve
 */
public class ModifyTipController implements Initializable {

    private Tip toBeModifiedTip;

    // for changing scenes
    private Display display;

    // database dependencies
    private Controller controller;

    // buttons
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button clearFieldsButton;

    // labels, used for errors for now
    @FXML
    private Label titleLabel;

    // input fields, remember to add new ones to the inputs list.
    private ArrayList<TextInputControl> inputs;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField tagField;
    @FXML
    private TextArea commentArea;

    /**
     * init function that is called when the view is created. Calls helper
     * methods that create eventhandlers for ui elements.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inputs = new ArrayList<>();
        fillInputList();
        clearFieldsButton();
        saveButton();
        cancelButton();
    }

    /**
     * Prefills the fields of the form with existing Tip data
     *
     * @param tip The tip that is to be modified
     */
    public void setTipToBeModified(Tip tip) {
        this.toBeModifiedTip = tip;
        titleField.setText(this.toBeModifiedTip.getTitle());
        authorField.setText(this.toBeModifiedTip.getAuthor());
        tagField.setText(this.toBeModifiedTip.getTagsToString());
        commentArea.setText(this.toBeModifiedTip.getSummary());
        urlField.setText(this.toBeModifiedTip.getUrl());
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setController(Controller c) {
        this.controller = c;
    }

    /**
     * Create an iterable list of inputfields.
     */
    private void fillInputList() {
        this.inputs.add(titleField);
        this.inputs.add(authorField);
        this.inputs.add(tagField);
        this.inputs.add(commentArea);
        this.inputs.add(urlField);
    }

    /**
     * Saves the updated tip into the database if the form validates, and then
     * redirects back to TipView
     */
    public void saveButton() {
        saveButton.setOnAction(event -> {
            //validate fields, just checking for empty now
            if (validate()) {
                titleLabel.setTextFill(Color.BLACK);
                saveTip();
                controller.addTags(this.toBeModifiedTip.getTags());
                controller.updateTip(this.toBeModifiedTip);
                Display.showTip(this.toBeModifiedTip);
            } else {
                titleLabel.setTextFill(Color.RED);
            }
        });
    }

    private boolean validate() {
        return !titleField.getText().trim().isEmpty();
    }

    /**
     * Clears all fields in inputs
     */
    private void clearFields() {
        for (TextInputControl tic : inputs) {
            tic.clear();
        }
    }

    private void clearFieldsButton() {
        this.clearFieldsButton.setOnAction(event -> {
            clearFields();
        });
    }

    /**
     * Cancels update, and redirects back to TipView
     */
    private void cancelButton() {
        this.cancelButton.setOnAction(event -> {
            Display.showTip(this.toBeModifiedTip);
        });
    }

    /**
     * Updates the attributes of the Tip object according to user input
     */
    private void saveTip() {
        this.toBeModifiedTip.setTitle(titleField.getText());
        this.toBeModifiedTip.setAuthor(authorField.getText());
        this.toBeModifiedTip.setSummary(commentArea.getText());
        this.toBeModifiedTip.setUrl(urlField.getText());

        ArrayList<String> tags = new ArrayList<>();
        if (!tagField.getText().isEmpty()) {
            tags.addAll(Arrays.asList(tagField.getText().split(",")));
        }
        this.toBeModifiedTip.setTags(tags);
    }
}
