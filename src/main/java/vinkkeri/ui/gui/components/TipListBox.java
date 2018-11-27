package vinkkeri.ui.gui.components;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import vinkkeri.ui.gui.Controller;
import vinkkeri.ui.gui.Display;

public class TipListBox extends VBox {

    public TipListBox(ListView lv) {
        //Keskelle sijoittuva listausnäkymä
        lv.tipsList.setEditable(true);
        Label lblHeader = new Label("Tip list");
        ArrayList<TableColumn> columns = new ArrayList<>();
        initTableColumns(columns, new String[]{"id", "Type", "Title", "Author", "URL", "ISBN", "Tags", "Comments", "Read", "Date", "Related Courses", "Required Courses"});
        lv.tipsList.getColumns().addAll(columns);

        Button button = new Button("Add Tip");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Display.setScene("add");
            }
        });

        setSpacing(5);
        setPadding(new Insets(10, 10, 10, 10));
        getChildren().addAll(lblHeader, lv.tipsList, button);
    }

    // Initialize all Table columns
    private void initTableColumns(ArrayList<TableColumn> columns, String[] names) {
        for (String name : names) {
            columns.add(new TableColumn(name));
        }
    }
}
