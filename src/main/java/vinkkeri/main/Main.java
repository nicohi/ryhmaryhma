package vinkkeri.main;

import javafx.application.Application;
import javafx.stage.Stage;
import vinkkeri.ui.gui.components.ListView;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
 /*---- Tein ListView-luokan, josta löytyy Graafinen käyttöliittymämalli
Siinä ei ole vielä linkkejä muualle, mutta parissa napissa on hieman toiminnallisuutta
kommentteja otetaan vastaan :)--- */
        ListView g = new ListView();
        Application.launch(ListView.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
