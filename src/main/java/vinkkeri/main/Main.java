package vinkkeri.main;

import vinkkeri.ui.Controller;
import vinkkeri.ui.Textui;

public class Main {

    public static void main(String[] args) {

        System.out.println("Ei vinkkeja");
        Controller controller = new Controller();
        Textui t = new Textui(controller);
        t.run();
    }
}
