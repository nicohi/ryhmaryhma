package vinkkeri;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class Stepdefs {

    @Given("^true is true$")
    public void true_is_true() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue("true", true);
    }

    @When("^true is not true$")
    public void true_is_not_true() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(!true != true);
    }

    @Then("^false is true$")
    public void false_is_true() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(!false);
    }


}
