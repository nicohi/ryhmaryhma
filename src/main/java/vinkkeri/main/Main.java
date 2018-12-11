package vinkkeri.main;

import javafx.application.Application;
import javafx.stage.Stage;
import vinkkeri.ui.gui.Display;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        new Display(primaryStage);
    }
}
