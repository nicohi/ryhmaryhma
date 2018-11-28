package vinkkeri.ui.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Display;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import vinkkeri.ui.gui.Controller;

public class AddTipController implements Initializable {

    // for changing scenes
    private Display display;

    // database dependencies
    private Controller controller;

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
        clearButton();
        addButton();
        backButton();
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
        this.inputs.add(isbnField);
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

    /**
     * fixme this to the real listview name!
     */
    private void backButton() {
        this.backButton.setOnAction(event -> {
            this.display.setScene("listview");
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
        String isbn = isbnField.getText();
        String comment = commentArea.getText();
        String url = urlField.getText();
        Tip tip = new Tip("book", title, author, comment, isbn, url, false);
        if (!tagField.getText().isEmpty()) {
            ArrayList<String> tags = new ArrayList<>();
            tags.addAll(Arrays.asList(tagField.getText().split(",")));
            tip.setTags(tags);
        }
        return tip;
    }
}
