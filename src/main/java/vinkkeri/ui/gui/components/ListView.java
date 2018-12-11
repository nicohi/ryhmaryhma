package vinkkeri.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Display;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tixkontt
 */
public class ListView {

    public final Separator separator = new Separator();
    public TableView tipsList = new TableView();
    public DatePicker datePicker = new DatePicker();
    public List<LabelTextInputControl> textItems = new ArrayList<>();

    public Display display;

    public ListView(Display d) {
        this.display = d;
        this.tipsList.setId("tipsList");
    }

    public Parent create() {
        separator.setMaxWidth(100.0);
        separator.setHalignment(HPos.LEFT);

        //  Luodaan päätason asettelu
        BorderPane layout = new BorderPane();
        layout.setPrefSize(800, 150);

        //vasen
        TipEditor menu = new TipEditor(this);
        //vasemman palkin komponentit
        menu.setMaxHeight(600.00);
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        TipListBox tipslistArea = new TipListBox(this);

        layout.setCenter(tipslistArea);

        //add searchbar
        layout.setTop(new SearchBar(this));

        return layout;
    }

    public void populateTipList(List<Tip> tips) {
        tipsList.setItems(FXCollections.observableArrayList(tips));
    }

    public void refreshTipList() {
        tipsList.setItems(FXCollections.observableArrayList(display.getController().getTips()));
    }

}
