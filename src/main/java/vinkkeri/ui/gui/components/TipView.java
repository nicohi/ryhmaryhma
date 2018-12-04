/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.Controller;
import vinkkeri.ui.gui.Display;

import java.util.Calendar;

/**
 * @author Olli K. Kärki
 */
public class TipView {

    private Controller controller;

    private Tip tip;

    private Label date;
    private Label type;
    private Label title;
    private Label author;
    private Label summary;
    private Label isbn;
    private TextField url;
    private Label read;
    private Label tags;

    public TipView(Controller controller) {
        this.controller = controller;
    }

    public Parent create() {
        VBox vb = new VBox();

        HBox titleLine = new HBox();
        titleLine.setId("titleLine");
        title = new Label("");
        titleLine.getChildren().addAll(new Label("Title: "), title);

        HBox authorLine = new HBox();
        authorLine.setId("authorLine");
        author = new Label("");
        authorLine.getChildren().addAll(new Label("Author: "), author);

        HBox typeLine = new HBox();
        typeLine.setId("typeLine");
        type = new Label("");
        typeLine.getChildren().addAll(new Label("Type: "), type);

        HBox dateLine = new HBox();
        dateLine.setId("dateLine");
        date = new Label("");
        dateLine.getChildren().addAll(new Label("Date Added: "), date);

        HBox isbnLine = new HBox();
        isbnLine.setId("isbnLine");
        isbn = new Label("");
        isbnLine.getChildren().addAll(new Label("ISBN: "), isbn);

        HBox urlLine = new HBox();
        urlLine.setId("urlLine");
        url = new TextField("");
        url.setPrefWidth(1000);
        url.setEditable(false);
        url.setBackground(Background.EMPTY);
        urlLine.getChildren().addAll(new Label("URL: "), url);
        url.setFocusTraversable(false);
        summary = new Label("");

        HBox readLine = new HBox();
        readLine.setId("readLine");
        read = new Label("");
        read.setId("readInfo");
        readLine.getChildren().addAll(new Label("Read: "), read);

        HBox tagLine = new HBox();
        tagLine.setId("tagLine");
        tags = new Label("");
        tagLine.getChildren().addAll(new Label("Tags: "), tags);

        Button back = new Button("Back");
        back.setId("back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Display.setScene("listview");
            }
        });

        Button flipread = new Button("Flip Read");
        flipread.setId("flipRead");
        flipread.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String read = tip.isRead();
                if (read == null || read.equals("false") || read.equals("")) {
                    String time = Calendar.getInstance().getTime().toString();
                    String parts[] = time.split(" ");
                    time = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
                    read = time;
                } else {
                    read = "";
                }
                tip.setRead(read);
                controller.markRead(read, tip.getId());
                setRead(tip.isRead());
                Display.clearrefresh();
            }
        });

        Button delete = new Button("Delete");
        delete.setId("deleteTip");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.removeTip(tip);
                controller.removeTags(tip.getTags());
                Display.refresh();
                Display.setScene("listview");
            }
        });

        vb.getChildren().addAll(titleLine, authorLine, typeLine, dateLine, isbnLine, urlLine, new Label(""), new Label("Summary"), summary, new Label(""), readLine, tagLine, new Label(""), flipread, new Label(""), back, new Label(""), delete);

        vb.setPadding(new Insets(10, 10, 10, 10));

        return vb;
    }

    public void setInfo(Tip tip) {
        this.tip = tip;
        this.title.setText(tip.getTitle());
        this.author.setText(tip.getAuthor());
        this.type.setText(tip.getType());
        this.date.setText(tip.getDate());
        this.isbn.setText(tip.getIsbn());
        this.url.setText(tip.getUrl());
        this.summary.setText(tip.getSummary());
        this.read.setText(tip.isRead());
        this.tags.setText(tip.getTags().toString());
    }

    private void setRead(String read) {
        this.read.setText(read);
    }

}
