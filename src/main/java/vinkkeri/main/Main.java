package vinkkeri.main;

import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import vinkkeri.ui.tui.ConsoleIO;
import vinkkeri.ui.tui.Controller;
import vinkkeri.ui.IO;
import vinkkeri.ui.gui.Display;
import vinkkeri.ui.tui.Textui;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch();
//        Controller controller = new Controller();
//	  IO io = new ConsoleIO(new Scanner(System.in), System.out);
//        Textui t = new Textui(controller, io);
//        t.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Display d = new Display(primaryStage);
    }
}
