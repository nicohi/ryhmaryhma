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
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Display d = new Display(primaryStage);
    }
}
