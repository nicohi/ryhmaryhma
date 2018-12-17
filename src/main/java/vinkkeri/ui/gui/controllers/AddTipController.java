package vinkkeri.ui.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import vinkkeri.objects.TagMap;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.DaoController;
import vinkkeri.ui.gui.Display;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddTipController implements Initializable {

    // for changing scenes
    private Display display;

    // database dependencies
    private DaoController controller;

    // for parsing tags from url
    private TagMap tagMap;


    // buttons
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button clearButton;

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
        this.tagMap = new TagMap();
        this.commentArea.setWrapText(true);
        fillInputList();
        clearButton();
        addButton();
        backButton();
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setController(DaoController c) {
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
     * Handles adding a new tip
     */
    public void addButton() {
        addButton.setOnAction(event -> {
            //validate fields, just checking for empty now
            if (validate()) {
                titleLabel.setTextFill(Color.BLACK);
                Tip tip = createTip();
                controller.addTags(tip.getTags());
                controller.insertTip(tip);
                clearFields();
            } else {
                titleLabel.setTextFill(Color.RED);
            }
        });
    }

    /**
     * Oma luokka tälle jos speksataan enemmän
     *
     * @return true if fields are ok
     */
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

    /**
     * Sets the eventhandler for clearbutton
     */
    private void clearButton() {
        this.clearButton.setOnAction(event -> {
            clearFields();
        });
    }

    private void backButton() {
        this.backButton.setOnAction(event -> {
            Display.setScene("listview");
        });
    }

    /**
     * Creates a tip object from the inputfields
     *
     * @return
     */
    private Tip createTip() {
        String title = titleField.getText();
        String author = authorField.getText();
        String comment = commentArea.getText().trim();
        String linkUrl = urlField.getText();
        Tip tip = new Tip(title, author, comment, linkUrl, "");
        ArrayList<String> tags = new ArrayList<>();
        if (!tagField.getText().isEmpty()) {
            tags.addAll(Arrays.asList(tagField.getText().split(",")));
        }
        List<String> foundTags = tagMap.find(linkUrl);
        for (String tag : foundTags) {
            if (!tags.contains(tag)) {
                tags.add(tag);
            }
        }
        tip.setTags(tags);
        return tip;
    }
}
