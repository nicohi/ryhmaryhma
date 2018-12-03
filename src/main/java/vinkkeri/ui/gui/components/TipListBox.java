package vinkkeri.ui.gui.components;

import java.util.ArrayList;
import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Display;

public class TipListBox extends VBox {
    
    private TableView table;
    
    public TipListBox(ListView lv) {
        //Keskelle sijoittuva listausnäkymä
        lv.tipsList.setEditable(true);
        Label lblHeader = new Label("Tip list");
        ArrayList<TableColumn> columns = new ArrayList<>();
        
        TableColumn title = new TableColumn("Title");
        title.setCellValueFactory(new PropertyValueFactory<Tip, String>("title"));
        title.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.22));
        columns.add(title);
        
        TableColumn author = new TableColumn("Author");
        author.setCellValueFactory(new PropertyValueFactory<Tip, String>("author"));
        author.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.15));
        columns.add(author);
        
        TableColumn tags = new TableColumn("Tags");
        tags.setCellValueFactory(new PropertyValueFactory<Tip, String>("tags"));
        tags.prefWidthProperty().bind(lv.tipsList.widthProperty().multiply(0.45));
        columns.add(tags);
        
        TableColumn read = new TableColumn("Read");
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
        flipReadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lv.tipsList.getSelectionModel().getSelectedItems().stream()
                        .forEach(tip -> {
                            Tip t = (Tip) tip;
                            String read = t.isRead();
                            if (read.equals("false") || read.equals("") || read == null) {
                                String time = Calendar.getInstance().getTime().toString();
                                String parts[] = time.split(" ");
                                time = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
                                read = time;
                            } else {
                                read = "";
                            }
                            t.setRead(read);
                            lv.display.getController().markRead(t.isRead(), t.getId());
                            lv.tipsList.getItems().clear();
                            Display.refresh();
                        });
            }
        });
        
        Button removeTipButton = new Button("Delete Tip");
        removeTipButton.setId("deleteTip");
        removeTipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lv.tipsList.getSelectionModel().getSelectedItems().stream()
                        .forEach(tip -> {
                            lv.display.getController().removeTip((Tip) tip);
                            lv.tipsList.getItems().remove(tip);
                            lv.display.getController().removeTags(((Tip) tip).getTags());
                        });
            }
        });

        // Tässä on tunnistus doubleclick ----------------------------------------------------------------------------------------------------------------- <- doubleclick on row
        lv.tipsList.setRowFactory(tv -> {
            TableRow<Tip> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Tip tip = row.getItem(); // Tämä on rivillä oleva tip objecti.
                    Display.showTip(tip);
                }
            });
            return row; // tästä ei oikeastaan tarvitse välittää
        });
        
        setSpacing(5);
        setPadding(new Insets(10, 10, 10, 10));
        getChildren().addAll(lblHeader, lv.tipsList, addTipButton, flipReadButton, removeTipButton);
        
        this.table = lv.tipsList;
    }
}
