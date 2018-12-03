package vinkkeri.ui.gui.components;

import vinkkeri.ui.gui.components.LabelTextInputControl;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Display;

/**
 *
 * @author tixkontt
 */
public class ListView {

    public final Separator separator = new Separator();
    public TableView tipsList = new TableView();
    public TextField txtDate = new TextField("Date");
    public DatePicker datePicker = new DatePicker();
    public List<LabelTextInputControl> textItems = new ArrayList<>();
    public ArrayList<RadioButton> rButtons2 = new ArrayList<>();
    public ArrayList<RadioButton> rButtons = new ArrayList<>();

    public Display display;

    public ListView(Display d) {
        this.display = d;
        this.tipsList.setId("tipsList"); // For testing purposes
    }

    public Parent create() {
        separator.setMaxWidth(100.0);
        separator.setHalignment(HPos.LEFT);

        //  Luodaan päätason asettelu
        BorderPane layout = new BorderPane();
        layout.setPrefSize(800, 150);

        final ToggleGroup tipTypes = new ToggleGroup();
        initRadioButtons(rButtons, new String[]{"Book", "Podcast", "Video", "Blogpost"}, tipTypes);

        //luettu /  ei luettu -komponentit
        final ToggleGroup grReadOrNot = new ToggleGroup();
        initRadioButtons(rButtons2, new String[]{"Not Read", "Read"}, grReadOrNot);

        //vasen
        TipEditor menu = new TipEditor(this);
        //vasemman palkin komponentit
        menu.setMaxHeight(600.00);
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        TipListBox tipslistArea = new TipListBox(this);

        // unused components
        //layout.setLeft(menu);
        //TipButtonBar toolbar = new TipButtonBar(this);
        //layout.setTop(toolbar);
        layout.setCenter(tipslistArea);

        //add searchbar
        layout.setTop(new SearchBar(this));

        return layout;
    }

    // Initialize radiobuttons that belong to the same group
    private void initRadioButtons(ArrayList<RadioButton> rbs, String[] names, ToggleGroup toggle) {
        for (String name : names) {
            RadioButton rb = new RadioButton(name);
            rbs.add(rb);
            //Tehdään radiobuttoneista ryhmä
            rb.setToggleGroup(toggle);
        }
    }

    public void populateTipList(List<Tip> tips) {
        tipsList.getItems().clear();
        tipsList.setItems(FXCollections.observableArrayList(tips));
    }

    public void refreshTipList() {
        tipsList.setItems(FXCollections.observableArrayList(display.getController().getTips()));
    }

}
