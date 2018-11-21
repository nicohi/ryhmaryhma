package vinkkeri.ui;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleIO implements IO {

    private Scanner input;
    private PrintStream output;

    public ConsoleIO() {
        this.input = new Scanner(System.in);
        this.output = System.out;
    }

    public ConsoleIO(Scanner scan, PrintStream str) {
        this.input = scan;
        this.output = str;
    }

    @Override
    public String readLine() {
        return this.input.nextLine();
    }

    @Override
    public void printLine(String s) {
        this.output.println(s);
    }

    @Override
    public void print(String s) {
        this.output.print(s);
    }

}
