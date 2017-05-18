package com.baeldung.serenity.spring.steps;

import com.baeldung.serenity.spring.KonamiCodeService;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.RandomStringUtils;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * @author aiet
 */
public class KonamiCodeConstructorDependencySteps {

    private String latestCheatcode;
    private boolean cheatSuccess;

    private KonamiCodeService konamiCodeService;

    public KonamiCodeConstructorDependencySteps(KonamiCodeService konamiCodeService) {
        this.konamiCodeService = konamiCodeService;
    }

    @Step("fetch latest cheat code")
    public void fetchLatestCheatcode() {
        latestCheatcode = konamiCodeService.getClassicCode();
    }

    @Step("cheat with latest code")
    public void cheatWithLatestcode() {
        cheatSuccess = konamiCodeService.cheatWith(latestCheatcode);
    }

    @Step("all stages of the game are cleared")
    public void gameStageCleared() {
        konamiCodeService.clearStage();
    }

    @Step("there is still a stage left")
    public void aStageRemains() {
        assertTrue("cheatcode wrong", cheatSuccess);
        assertThat(konamiCodeService.stageLeft(), equalTo(1));
    }

    //    @Rule public SpringIntegrationMethodRule methodRule = new SpringIntegrationMethodRule();

    //    @DirtiesContext
    @Step
    public void letsHack() {
        konamiCodeService.alterClassicCode(RandomStringUtils.random(4));
    }

    @Step("there is no stage left")
    public void noStageRemains() {
        assertFalse(cheatSuccess);
        assertThat(konamiCodeService.stageLeft(), equalTo(0));
    }
}
