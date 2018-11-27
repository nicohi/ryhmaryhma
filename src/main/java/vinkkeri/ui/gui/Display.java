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
    private static ListView listview;
    private static Controller controller;
    
    public Display(Stage stage) {
        Display.stage = stage;
        this.controller = new Controller();
        initialize();
    }
    
    private void initialize() {
        scenes = new HashMap<>();
        loaders = new HashMap<>();

        // add view
        initializeScene("fxml/AddTipView.fxml", "add");
        listview = new ListView(this);
        addSceneNonFXML(new Scene(listview.create(), WIDTH, HEIGHT), "listview");
        setAddViewDependencies();
        
        stage.setTitle("Vinkkeri");
        stage.setScene(Display.scenes.get("listview"));
        listview.populateTipList(controller.getTips());
        stage.show();
    }
    
    private void addSceneNonFXML(Scene s, String name) {
        scenes.put(name, s);
    }
    
    private void setAddViewDependencies() {
        AddTipController c = loaders.get("add").getController();
        c.setController(controller);
        c.setDisplay(this);
    }

	public static Controller getController() {
		return controller;
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
        if(scene.equals("listview")) {
            listview.populateTipList(controller.getTips());
        }
    }
}
