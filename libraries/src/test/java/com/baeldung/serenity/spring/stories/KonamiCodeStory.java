package com.baeldung.serenity.spring.stories;

import com.baeldung.serenity.spring.steps.KonamiCodeRestSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * @author aiet
 */
public class KonamiCodeStory {

    @Steps KonamiCodeRestSteps restSteps;

    @Given("game stage cleared")
    public void givenStageCleared(){
        restSteps.stageCleared();
    }

    @Given("KONAMI cheat code")
    public void givenKONAMICheatCode() throws Exception{
        restSteps.givenClassicCheatCode();
    }

    @When("I input the cheat code")
    public void whenIInputTheCheatCode() {
        restSteps.whenCheat();
    }

    @Then("a hidden stage will be unlocked")
    public void thenAHiddenStageWillBeUnlocked() {
        restSteps.thenClassicCodeCanDoTheTrick();
        restSteps.thenMoreStages();
    }

}
