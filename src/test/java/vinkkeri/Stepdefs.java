package vinkkeri;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.database.TipDao;
import vinkkeri.objects.Tip;
import vinkkeri.ui.ConsoleIO;
import vinkkeri.ui.Controller;
import vinkkeri.ui.IO;
import vinkkeri.ui.StubIO;
import vinkkeri.ui.Textui;

public class Stepdefs {

    Controller controller = new Controller();
    List<String> lines = new ArrayList<>();
    TipDao td = new SQLiteTipDao("jdbc:sqlite:database.db");
    StubIO io = new StubIO();
    Textui ui = new Textui(controller, io);

    @Given("^new tip is selected$")
    public void new_tip_is_selected() throws Throwable {
        lines.add("new");

    }

    @When("^title \"([^\"]*)\" is entered$")
    public void title_is_entered(String title) throws Throwable {
        lines.add(title);
    }

    @When("^author \"([^\"]*)\" is entered$")
    public void author_is_entered(String author) throws Throwable {
        lines.add(author);
    }

    @When("^comment \"([^\"]*)\" is entered$")
    public void comment_is_entered(String comment) throws Throwable {
        lines.add(comment);
    }

    @When("^isbn \"([^\"]*)\" is entered$")
    public void isbn_is_entered(String isbn) throws Throwable {
        lines.add(isbn);
    }

    @Then("^new tip with title \"([^\"]*)\" is stored in the program$")
    public void false_is_true(String title) throws Throwable {
        setLinesAndRun();
        boolean exists = false;

        for (Tip tip : td.getTips()) {
            if (tip.getTitle().equals(title)) {
                exists = true;
                break;
            }
        }

        if (exists) {
            td.remove(td.getNewestID());
        }

        assertTrue(exists);
    }

    @Given("^user interface is initialized$")
    public void user_interface_is_initialized() throws Throwable {
        lines.clear();
    }

    @When("^command \"([^\"]*)\" is entered$")
    public void command_is_entered(String command) throws Throwable {
        lines.add(command);
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) throws Throwable {
        setLinesAndRun();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    private void setLinesAndRun() {
        lines.add("quit");
        io.setLines(lines);
        ui.run();
    }
}
