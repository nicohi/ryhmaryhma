/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Olli K. Kärki
 */
public class Display {
    
    public static final int WIDTH = 1280, HEIGHT = 720;
    
    private static Stage stage;
    private static HashMap<String, Scene> scenes;
    
    public Display(Stage stage) {
        Display.stage = stage;
        initialize();
    }
    
    private void initialize() {
        scenes = new HashMap<>();
        
        initializeScene("fxml/test.fxml", "listing");
        initializeScene("fxml/AddTipView.fxml", "add");
        
        stage.setTitle("Vinkkeri");
        stage.setScene(Display.scenes.get("add"));
        stage.show();
    }
    
    private void initializeScene(String path, String name) {
        URL location = getClass().getResource(path);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
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
