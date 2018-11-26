package vinkkeri.main;

import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import vinkkeri.ui.tui.ConsoleIO;
import vinkkeri.ui.tui.Controller;
import vinkkeri.ui.IO;
import vinkkeri.ui.gui.Details;
import vinkkeri.ui.gui.Display;
import vinkkeri.ui.gui.GGui;
import vinkkeri.ui.tui.Textui;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
 /*---- Tein GGui-luokan, josta löytyy Graafinen käyttöliittymämalli
Siinä ei ole vielä linkkejä muualle, mutta parissa napissa on hieman toiminnallisuutta
kommentteja otetaan vastaan :)--- */
        GGui g = new GGui();
        Application.launch(GGui.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
