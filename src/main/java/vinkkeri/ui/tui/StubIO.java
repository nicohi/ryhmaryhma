/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.tui;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import vinkkeri.ui.IO;

/**
 *
 * @author Olli K. Kärki
 */
public class StubIO implements IO {

    private List<String> lines;
    private int i;
    private int li;
    private ArrayList<String> prints;
    private PrintStream output;

    public StubIO() {
        this.output = System.out;
        i = 0;
        li = 0;
        prints = new ArrayList<>();
    }

    public StubIO(List<String> values) {
        this.output = System.out;
        this.lines = values;
        i = 0;
        li = 0;
        prints = new ArrayList<>();
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public String readLine() {
        int r = i;
        if (i < lines.size()) {
            i++;
        }
        return lines.get(r);
    }

    @Override
    public void printLine(String s) {
        prints.add(s);
        li++;
        this.output.println(s);
    }

    @Override
    public void print(String s) {
        prints.add(s);
        this.output.print(s);
    }

    public ArrayList<String> getPrints() {
        return prints;
    }
}
