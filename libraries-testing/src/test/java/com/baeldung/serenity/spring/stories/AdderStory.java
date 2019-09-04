package com.baeldung.serenity.spring.stories;

import com.baeldung.serenity.spring.steps.AdderRestSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * @author aiet
 */
public class AdderStory {

    @Steps
    AdderRestSteps restSteps;

    @Given("a number")
    public void givenANumber() throws Exception {
        restSteps.givenCurrentNumber();
    }

    @When("I submit another number $num to adder")
    public void whenISubmitToAdderWithNumber(int num) {
        restSteps.whenAddNumber(num);
    }

    @Then("I get a sum of the numbers")
    public void thenIGetTheSum() {
        restSteps.thenSummedUp();
    }

}
