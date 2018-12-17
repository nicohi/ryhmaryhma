package vinkkeri.ui.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.components.ListView;
import vinkkeri.ui.gui.components.TipView;
import vinkkeri.ui.gui.controllers.AddTipController;
import vinkkeri.ui.gui.controllers.ModifyTipController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import vinkkeri.ui.gui.controllers.TipListUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Olli K. Kärki
 */
public class Display {

    public static final int WIDTH = 1280, HEIGHT = 720;

    private static Stage stage;
    private static HashMap<String, Scene> scenes;
    private static HashMap<String, FXMLLoader> loaders;
    private static ListView listview;
    private static TipView tipview;
    private static DaoController controller;

    public Display(Stage stage, DaoController controller) {
        Display.stage = stage;
        Display.controller = controller;
        initialize();
    }

    public static DaoController getController() {
        return controller;
    }

    private void addSceneNonFXML(Scene s, String name) {
        scenes.put(name, s);
    }

    private void setAddViewDependencies() {
        AddTipController c = loaders.get("add").getController();
        c.setController(controller);
        c.setDisplay(this);
    }

    private void initialize() {
        scenes = new HashMap<>();
        loaders = new HashMap<>();

        // add view
        initializeScene("fxml/AddTipView.fxml", "add");
        initializeScene("fxml/ModifyTipView.fxml", "modify");
        listview = new ListView(this);
        addSceneNonFXML(new Scene(listview.create(), WIDTH, HEIGHT), "listview");
        tipview = new TipView(controller);
        addSceneNonFXML(new Scene(tipview.create(), WIDTH, HEIGHT), "tipview");
        setAddViewDependencies();
        setModifyViewDependencies();

        stage.setTitle("Vinkkeri");
        stage.setScene(Display.scenes.get("listview"));
		TipListUtils.populateTipList(listview, controller.getTips());
        stage.show();
    }

    private void setModifyViewDependencies() {
        ModifyTipController m = loaders.get("modify").getController();
        m.setController(controller);
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
        if (scene.equals("listview")) {
			TipListUtils.populateTipList(listview, controller.getTips());
        }
    }

    public static void setSceneAndTip(String scene, Tip tip) {
        ModifyTipController mc = loaders.get("modify").getController();
        mc.setTipToBeModified(tip);
        stage.setScene(scenes.get(scene));
    }

    public static void showTip(Tip tip) {
        tipview.setInfo(tip);
        stage.setScene(scenes.get("tipview"));
    }

    public static void refresh() {
		TipListUtils.populateTipList(listview, controller.getTips());
    }

    public static void clearAndRefresh() {
		listview.tipsList.getItems().clear();
		refresh();
    }

    public static void refreshWithSearchAndHide() {
		TipListUtils.refreshTips(listview, listview.searchBar.getSearchTerms(), listview.searchBar.getReadStatus());
    }
}
