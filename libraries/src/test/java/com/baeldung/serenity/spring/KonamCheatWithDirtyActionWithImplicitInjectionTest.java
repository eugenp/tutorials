package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.KonamiCodeServiceInjectionSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author aiet
 */
@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = KonamiCodeService.class)
public class KonamCheatWithDirtyActionWithImplicitInjectionTest {

    @Steps private KonamiCodeServiceInjectionSteps cheatSteps;

    @Test
    @Title("hidden stage is not unlocked after cheating (cheatcode hacked)")
    public void givenGameStageCleared_whenCheat_thenCheatFail() {
        fetchCodeAndCheat();
        cheatSteps.noStageRemains();
    }

    private void fetchCodeAndCheat() {
        cheatSteps.gameStageCleared();
        cheatSteps.fetchLatestCheatcode();
        cheatSteps.cheatWithLatestcode();
    }

    @Rule public SpringIntegrationMethodRule springIntegration = new SpringIntegrationMethodRule();

    @DirtiesContext
    @Test
    @Title("altering the cheatcode after unlocking would stop others from cheating")
    public void givenGameStageCleared_whenCheatAndHack_thenAnotherCheatFail() {
        fetchCodeAndCheat();
        cheatSteps.aStageRemains();

        cheatSteps.letsHack();

        fetchCodeAndCheat();
        cheatSteps.noStageRemains();
    }

}
