package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.KonamiCodeServiceInjectionSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author aiet
 */
@RunWith(SerenityRunner.class)
public class KonamCheatWithServiceTest {

    @Steps private KonamiCodeServiceInjectionSteps cheatSteps;

    @Test
    @Title("hidden stage should be unlocked after cheating (mockmvc)")
    public void givenGameStageCleared_whenCheat_thenHiddenStageUnlocked() {
        cheatSteps.gameStageCleared();
        cheatSteps.fetchLatestCheatcode();
        cheatSteps.cheatWithLatestcode();
        cheatSteps.aStageRemains();
    }

}
