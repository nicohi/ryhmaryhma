package vinkkeri.main;


import javafx.application.Application;
import javafx.stage.Stage;
import vinkkeri.ui.gui.Display;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Display d = new Display(primaryStage);
    }
}
