package vinkkeri.ui.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vinkkeri.ui.gui.components.ListView;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.database.TagDao;
import vinkkeri.database.TipDao;
import vinkkeri.ui.gui.controllers.AddTipController;

/**
 * @author Olli K. Kärki
 */
public class Display {

    public static final int WIDTH = 1280, HEIGHT = 720;

    private static Stage stage;
    private static HashMap<String, Scene> scenes;
    private static HashMap<String, FXMLLoader> loaders;
    private TipDao tipDao;
    private TagDao tagDao;

    public Display(Stage stage) {
        Display.stage = stage;
        this.tipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
        this.tagDao = new SQLiteTagDao("jdbc:sqlite:database.db");
        initialize();
    }

    private void initialize() {
        scenes = new HashMap<>();
        loaders = new HashMap<>();
        
        // add view
        initializeScene("fxml/AddTipView.fxml", "add");
        ListView lv = new ListView();
        addSceneNonFXML(new Scene(lv.create(), WIDTH, HEIGHT), "listview");
        setAddViewDependencies();

        stage.setTitle("Vinkkeri");
        stage.setScene(Display.scenes.get("listview"));
        stage.show();
    }

    private void addSceneNonFXML(Scene s, String name) {
        scenes.put(name, s);
    }

    private void setAddViewDependencies() {
        AddTipController c = loaders.get("add").getController();
        c.setTipDao(tipDao);
        c.setTagDao(tagDao);
        c.setDisplay(this);
    }

    private void initializeScene(String path, String name) {
        URL location = getClass().getResource(path);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        loaders.put(name, fxmlLoader);
        try {
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
            scenes.put(name, scene);
        } catch (IOException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setScene(String scene) {
        stage.setScene(scenes.get(scene));
    }
}
