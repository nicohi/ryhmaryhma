/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui;

import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author tixkontt
 */
public class GGui extends Application {

    public final Separator separator = new Separator();
    public TableView tipsList = new TableView();

    @Override
    public void start(Stage window) throws Exception {
        //nimetään ikkuna
        window.setTitle("Vinkkeri");
        //  Luodaan päätason asettelu
        BorderPane layout = new BorderPane();
        layout.setPrefSize(800, 150);

        // toolbar, jossa painikkeet uusi, tallenna, tyhjennä, etsi ja poista
        HBox buttons = new HBox();

        //Keskelle sijoittuva listausnäkymä
        tipsList.setEditable(true);
        Label lblHeader = new Label("Tip list");
        TableColumn colId = new TableColumn("id");
        TableColumn colType = new TableColumn("Type");
        TableColumn colTitle = new TableColumn("Title");
        TableColumn colAuthor = new TableColumn("Author");
        TableColumn colURL = new TableColumn("URL");
        TableColumn colISBN = new TableColumn("ISBN");
        TableColumn colTags = new TableColumn("Tags");
        TableColumn colComments = new TableColumn("Comments");
        TableColumn colReadOrNot = new TableColumn("Read");
        TableColumn colDate = new TableColumn("Date");
        TableColumn colRelC = new TableColumn("Related Courses");
        TableColumn colReqC = new TableColumn("Requested Courses");
        tipsList.getColumns().addAll(colId, colType, colTitle, colAuthor, colURL, colISBN, colTags, colComments, colReadOrNot, colDate, colRelC, colReqC);
        VBox tipslistArea = new VBox();
        tipslistArea.setSpacing(5);
        tipslistArea.setPadding(new Insets(10, 0, 0, 10));
        tipslistArea.getChildren().addAll(lblHeader, tipsList);
        //vasemman palkin komponentit
        VBox menu = new VBox();
        menu.setMaxHeight(600.00);
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        //Luodaan päätason komponentit
        Label lblID = new Label("ID");
        TextField txtId = new TextField("Id");

//        Separator separator = new Separator();
        //Tipsien tyyppi
        RadioButton rbBook = new RadioButton("Book");
        RadioButton rbPodcast = new RadioButton("Podcast");
        RadioButton rbVideo = new RadioButton("Video");
        RadioButton rbBlogPost = new RadioButton("Blogpost");

        //jos rbPodcast tai rbVideo = true, tuodaan lbl ja textfield näkyviin
        Label lblUrl = new Label("Url");
        TextField txtUrl = new TextField("Url");

        //Tehdään radiobuttoneista ryhmä
        final ToggleGroup tipTypes = new ToggleGroup();
        rbBook.setToggleGroup(tipTypes);
        rbPodcast.setToggleGroup(tipTypes);
        rbVideo.setToggleGroup(tipTypes);
        rbBlogPost.setToggleGroup(tipTypes);

        //luettu /  ei luettu -komponentit
        final ToggleGroup grReadOrNot = new ToggleGroup();
        Label lblReadOrNot = new Label("Read or Not");
        RadioButton rbNotRead = new RadioButton("Not Read");
        rbNotRead.setToggleGroup(grReadOrNot);
        RadioButton rbRead = new RadioButton("Read");
        rbRead.setToggleGroup(grReadOrNot);
        TextField txtDate = new TextField("Date");
        DatePicker datePicker = new DatePicker();


        //lisää komponentteja
        Label lblTitle = new Label("Title");
        TextField txtTitle = new TextField("Title");

        Label lblAuthor = new Label("Author");
        TextField txtAuthor = new TextField("Author");

        Label lblISBN = new Label("ISBN");
        TextField txtISBN = new TextField("ISBN");

        Label lblTags = new Label("Tags");
        TextField txtTags = new TextField("Tags");

        Label lblComments = new Label("Comments");
        TextArea txtAreaComments = new TextArea("Comments");

        // Luodaan valikon napit
        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction((e) -> {
            System.out.println("painettu delete");
        });

        Button btnFindTip = new Button("Find Tip");
        btnFindTip.setOnAction((p) -> {
            System.out.println("painettu Find Tip");
        });

        Button btnSave = new Button("Save");
        btnSave.setOnAction((e) -> {
            System.out.println("painettu tallennusta");
        });

        Button btnNewTip = new Button("New Tip");
        btnNewTip.setOnAction((n) -> {
            System.out.println("painettu uusi vinkki");
        });

        Button btnClear = new Button("Clear");
        btnClear.setOnAction((ActionEvent c) -> {
            txtId.clear();
            txtTitle.clear();
            txtAuthor.clear();
            txtUrl.clear();
            txtISBN.clear();
            txtTags.clear();
            txtAreaComments.clear();
            txtDate.clear();
            datePicker.setValue(null);

            rbBook.setSelected(false);
            rbBlogPost.setSelected(false);
            rbNotRead.setSelected(false);
            rbPodcast.setSelected(false);
            rbRead.setSelected(false);
            rbVideo.setSelected(false);

            System.out.println("painettu clear");
        });

        //luodaan toobar ja lisätään napit sinne
        ToolBar toolbar = new ToolBar(btnClear, btnFindTip, btnNewTip, btnSave, btnDelete);

        /*---- luodaan layout ----*/
        //lisätään vasemman palkin komponentit:
        menu.getChildren().add(lblID);
        menu.getChildren().add(txtId);

        // Lisätään radiobuttonit
        menu.getChildren().add(rbBook);
        menu.getChildren().add(rbPodcast);
        menu.getChildren().add(rbVideo);
        menu.getChildren().add(rbBlogPost);
//        menu.getChildren().add(rbOther);
        separator.setMaxWidth(100.0);
        separator.setHalignment(HPos.LEFT);

        //lisätään separator
        menu.getChildren().add(6, separator);

        //lisätään otsikko
        menu.getChildren().add(lblTitle);
        menu.getChildren().add(txtTitle);

        //lisätään kirjoittaja
        menu.getChildren().add(lblAuthor);
        menu.getChildren().add(txtAuthor);

        //lisätään url-kenttä
        menu.getChildren().add(lblUrl);
        menu.getChildren().add(txtUrl);

        //lisätään ISBN-kenttä
        menu.getChildren().add(lblISBN);
        menu.getChildren().add(txtISBN);

        //lisätään tagi-kenttä
        menu.getChildren().add(lblTags);
        menu.getChildren().add(txtTags);

        //lisätään kommenttikenttä
        menu.getChildren().add(lblComments);
        menu.getChildren().add(txtAreaComments);

        //lisätään lukutiedot
        menu.getChildren().add(lblReadOrNot);
        menu.getChildren().add(rbNotRead);
        menu.getChildren().add(rbRead);
        menu.getChildren().add(datePicker);

//        menu.getChildren().addAll(btnNewTip, btnSave, btnDelete);
        layout.setLeft(menu);
        layout.setTop(toolbar);
        layout.setCenter(tipslistArea);

        // 6. Luodaan päänäkymä ja asetetaan päätason asettelu siihen
        Scene vinkkeri = new Scene(layout, 1800, 800);

        // 7. Näytetään sovellus
        window.setScene(vinkkeri);
        window.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
