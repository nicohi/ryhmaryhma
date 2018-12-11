package vinkkeri.ui.gui.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TipEditor extends VBox {

    public TipEditor(ListView lv) {

        //Luodaan päätason komponentit
        initMenuItems(lv.textItems, new TextField(), "ID", "Id");
        initMenuItems(lv.textItems, new TextField(), "Url", "Url");
        initMenuItems(lv.textItems, new TextField(), "Title", "Title");
        initMenuItems(lv.textItems, new TextField(), "Author", "Author");
        initMenuItems(lv.textItems, new TextField(), "ISBN", "ISBN");
        initMenuItems(lv.textItems, new TextField(), "Tags", "Tags");
        initMenuItems(lv.textItems, new TextArea(), "Comments", "Comments");

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(lv.textItems.get(0).getLabel());
        nodes.add(lv.textItems.get(0).getField());

        nodes.add(lv.separator);

        Label lblReadOrNot = new Label("Read or Not");
        /*---- luodaan layout ----*/
        //lisätään vasemman palkin komponentit:
        // !!!HUOM!!! 1. komponentti jo lisätty manuaalisesti
        for (int i = 1; i < lv.textItems.size(); i++) {
            nodes.add(lv.textItems.get(i).getLabel());
            nodes.add(lv.textItems.get(i).getField());
        }
        getChildren().addAll(nodes);

        //lisätään lukutiedot
        getChildren().add(lblReadOrNot);
        getChildren().add(lv.datePicker);

    }

    // Initialize all Label-TextField pairs
    private void initMenuItems(List<LabelTextInputControl> items, TextInputControl text, String labelName, String promptText) {
        items.add(new LabelTextInputControl(labelName, text, promptText));
    }

}
