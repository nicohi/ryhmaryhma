package vinkkeri;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.database.TipDao;
import vinkkeri.objects.Tip;
import vinkkeri.ui.tui.Controller;
import vinkkeri.ui.tui.StubIO;
import vinkkeri.ui.tui.Textui;

public class Stepdefs {

    Controller controller = new Controller();
    List<String> lines = new ArrayList<>();
    TipDao td = new SQLiteTipDao("jdbc:sqlite:database.db");
    StubIO io = new StubIO();
    Textui ui = new Textui(controller, io);

    @Given("^new tip is selected$")
    public void new_tip_is_selected() {
        lines.add("new");

    }

    @When("^title \"([^\"]*)\" is entered$")
    public void title_is_entered(String title) {
        lines.add(title);
    }

    @When("^author \"([^\"]*)\" is entered$")
    public void author_is_entered(String author) {
        lines.add(author);
    }

    @When("^comment \"([^\"]*)\" is entered$")
    public void comment_is_entered(String comment) {
        lines.add(comment);
    }

    @When("^isbn \"([^\"]*)\" is entered$")
    public void isbn_is_entered(String isbn) {
        lines.add(isbn);
    }

    @Then("^new tip with title \"([^\"]*)\" is stored in the program$")
    public void false_is_true(String title) {
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
    public void user_interface_is_initialized() {
        lines.clear();
    }

    @When("^command \"([^\"]*)\" is entered$")
    public void command_is_entered(String command) {
        lines.add(command);
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) {
        setLinesAndRun();
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("^system will output a line containing \"([^\"]*)\"$")
    public void system_will_output_a_line_containing(String expectedOutput) {
        setLinesAndRun();
        assertTrue(io.getPrints().toString().contains(expectedOutput));
    }

    private void setLinesAndRun() {
        lines.add("quit");
        io.setLines(lines);
        ui.run();
    }
}
