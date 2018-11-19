package vinkkeri.ui;

import vinkkeri.objects.Tip;

import java.util.List;
import java.util.Scanner;

public class Textui {

    private Scanner scanner;
    private Controller controller;

    public Textui(Controller controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void run() {
        while (true) {
            System.out.println("Input command: [new, list, quit]");
            String command = scanner.nextLine();
            if (command.equals("quit")) {
                break;
            }
            if (command.equals("new")) {
                newBookTip();
            }
            if (command.equals("list")) {
                printTips();
            };
        }
    }


    public void newBookTip() {
        System.out.println();
        System.out.println("Title:");
        String title = scanner.nextLine();
        System.out.println("Author:");
        String author = scanner.nextLine();
        System.out.println("Comment:");
        String summary = scanner.nextLine();
        System.out.println("ISBN: (Can be empty)");
        String isbn = scanner.nextLine();
        String type = "book";
        String url = "";
        boolean read = false;
        Tip tip = new Tip(type, title, author, summary, isbn, url, read);
        controller.newTip(tip);
    }

    public void printTips() {
        List<Tip> tips = controller.getTips();
        for (Tip tip : tips) {
            System.out.println(tip);
            System.out.println("");
        }
    }


}
