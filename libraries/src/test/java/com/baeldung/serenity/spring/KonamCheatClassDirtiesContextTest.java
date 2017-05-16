package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.KonamiCodeServiceInjectionSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationClassRule;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

/**
 * @author aiet
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
                      KonamCheatClassDirtiesContextTest.DirtiesContextTest.class, KonamCheatClassDirtiesContextTest.AnotherDirtiesContextTest.class
                    })
public class KonamCheatClassDirtiesContextTest {

    @RunWith(SerenityRunner.class)
    @ContextConfiguration(classes = KonamiCodeService.class)
    public static abstract class Base {

        @Steps KonamiCodeServiceInjectionSteps cheatSteps;

        @ClassRule public static SpringIntegrationClassRule springIntegrationClassRule = new SpringIntegrationClassRule();

        void hiddenStageShouldBeUnlockedAfterCheating() {
            fetchAndCheat();
            cheatSteps.aStageRemains();

            cheatSteps.letsHack();

            fetchAndCheat();
            cheatSteps.noStageRemains();
        }

        private void fetchAndCheat() {
            cheatSteps.gameStageCleared();
            cheatSteps.fetchLatestCheatcode();
            cheatSteps.cheatWithLatestcode();
        }

    }

    @DirtiesContext(classMode = AFTER_CLASS)
    public static class AnotherDirtiesContextTest extends Base {

        @Test
        @Title("altering the cheatcode after unlocking would stop others from cheating, not affected by other tests (another)")
        public void givenGameStageCleared_whenCheat_thenHiddenStageUnlocked() {
            super.hiddenStageShouldBeUnlockedAfterCheating();
        }
    }

    @DirtiesContext(classMode = AFTER_CLASS)
    public static class DirtiesContextTest extends Base {

        @Test
        @Title("altering the cheatcode after unlocking would stop others from cheating, not affected by other tests")
        public void givenGameStageCleared_whenCheat_thenHiddenStageUnlocked() {
            super.hiddenStageShouldBeUnlockedAfterCheating();
        }
    }


}
