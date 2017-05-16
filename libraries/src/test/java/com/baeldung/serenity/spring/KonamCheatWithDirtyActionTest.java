package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.KonamiCodeConstructorDependencySteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.thucydides.core.annotations.Title;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author aiet
 */
@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = KonamiCodeService.class)
public class KonamCheatWithDirtyActionTest {

    private KonamiCodeConstructorDependencySteps cheatSteps;

    @Autowired private KonamiCodeService codeService;

    @Before
    public void init() {
        cheatSteps = new KonamiCodeConstructorDependencySteps(codeService);
    }

    @Test
    @Title("hidden stage should be unlocked after cheating (run in service with dirty action)")
    public void givenGameStageCleared_whenCheat_thenHiddenStageUnlocked() {
        fetchCodeAndCheat();
        cheatSteps.aStageRemains();
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

    private void fetchCodeAndCheat() {
        cheatSteps.gameStageCleared();
        cheatSteps.fetchLatestCheatcode();
        cheatSteps.cheatWithLatestcode();
    }

}
