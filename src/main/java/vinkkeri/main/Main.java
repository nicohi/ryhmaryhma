package vinkkeri.main;

import javafx.application.Application;
import javafx.stage.Stage;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.ui.gui.DaoController;
import vinkkeri.ui.gui.Display;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String dbAddress = "true".equals(System.getProperty("use.test.db")) ? "jdbc:sqlite:test.db" : "jdbc:sqlite:database.db";
        DaoController controller = new DaoController(new SQLiteTipDao(dbAddress), new SQLiteTagDao(dbAddress));
        new Display(primaryStage, controller);
    }
}
