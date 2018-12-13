package vinkkeri.ui.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Display;

import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.layout.HBox;

public class TipListBox extends VBox {

    public TipListBox(ListView lv) {
        //Keskelle sijoittuva listausnäkymä
        lv.tipsList.setEditable(true);
        ArrayList<TableColumn> columns = new ArrayList<>();

        TableColumn title = new TableColumn("Title");
        title.setId("title");
        title.setCellValueFactory(new PropertyValueFactory<Tip, String>("title"));
        title.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.22));
        columns.add(title);

        TableColumn author = new TableColumn("Author");
        author.setId("author");
        author.setCellValueFactory(new PropertyValueFactory<Tip, String>("author"));
        author.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.15));
        columns.add(author);

        TableColumn tags = new TableColumn("Tags");
        tags.setId("tags");
        tags.setCellValueFactory(new PropertyValueFactory<Tip, String>("tags"));
        tags.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.45));
        columns.add(tags);

        TableColumn read = new TableColumn("Read");
        read.setId("read");
        read.setCellValueFactory(new PropertyValueFactory<Tip, String>("read"));
        read.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.20));
        columns.add(read);

        lv.tipsList.getColumns().addAll(columns);

        Button addTipButton = new Button("Add Tip");
        addTipButton.setId("addTip");
        addTipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Display.setScene("add");
            }
        });

        Button flipReadButton = new Button("Flip Read");
        flipReadButton.setId("flipRead");
        flipReadButton.setOnAction(event -> lv.tipsList.getSelectionModel().getSelectedItems().stream()
                .forEach(tip -> {
                    Tip t = (Tip) tip;
                    String read1 = t.isRead();
                    if (read1 == null || read1.equals("false") || read1.equals("")) {
                        String time = Calendar.getInstance().getTime().toString();
                        String parts[] = time.split(" ");
                        time = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
                        read1 = time;
                    } else {
                        read1 = "";
                    }
                    t.setRead(read1);
                    Display.getController().markRead(t.isRead(), t.getId());
                    Display.clearAndRefresh();
                }));

        Button removeTipButton = new Button("Delete Tip");
        removeTipButton.setId("deleteTip");
        removeTipButton.setOnAction(event -> lv.tipsList.getSelectionModel().getSelectedItems().stream()
                .forEach(tip -> {
                    Display.getController().removeTip((Tip) tip);
                    lv.tipsList.getItems().remove(tip);
                    Display.getController().removeTags(((Tip) tip).getTags());
                }));

        // Tässä on tunnistus doubleclick ----------------------------------------------------------------------------------------------------------------- <- doubleclick on row
        lv.tipsList.setRowFactory(tv -> {
            TableRow<Tip> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Tip tip = row.getItem(); // Tämä on rivillä oleva tip objecti.
                    Display.showTip(tip);
                }
            });
            return row;
        });

        setSpacing(5);
        setPadding(new Insets(10, 10, 10, 10));
        HBox hbox = new HBox();
        hbox.getChildren().addAll(addTipButton, flipReadButton, removeTipButton);
        getChildren().addAll(lv.tipsList, hbox);
    }
}
