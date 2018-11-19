package vinkkeri.main;

import java.util.Scanner;
import vinkkeri.ui.ConsoleIO;
import vinkkeri.ui.Controller;
import vinkkeri.ui.IO;
import vinkkeri.ui.Textui;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
		IO io = new ConsoleIO(new Scanner(System.in), System.out);
        Textui t = new Textui(controller, io);
        t.run();
    }
}
