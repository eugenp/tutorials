package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.KonamiCheatSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(SerenityRunner.class)
@ContextConfiguration(locations = "classpath:konami-cheat-beans.xml")
public class KonamiCheatWithIntegrationMethodRulesTest {

    private static Logger LOG = LoggerFactory.getLogger(KonamiCheatWithIntegrationMethodRulesTest.class);

    @BeforeClass
    public static void initClass() {
        LOG.info("static chaincode before test class: {}", staticCheatCode);
    }

    @AfterClass
    public static void destroyClass() {
        LOG.info("static chaincode after test class: {}", staticCheatCode);
    }

    @Before
    public void init() {
        staticCheatCode = cheatCode;
        LOG.info("cheatcode before test: {}", cheatCode);
    }

    @After
    public void destroy() {
        LOG.info("cheatcode after test: {}", cheatCode);
    }

    @Rule public SpringIntegrationMethodRule springMethodIntegration = new SpringIntegrationMethodRule();

    @Steps private KonamiCheatSteps konamiCheatSteps;

    @Value("#{konami_props['code']}") private String cheatCode;

    private static String staticCheatCode;

    @Test
    @Title("hidden stage should be unlocked after cheating (rule integration)")
    public void givenGameStageCleared_whenCheat_thenHiddenStageUnlocked() {
        konamiCheatSteps.gameStageCleared();
        konamiCheatSteps.cheatWith(cheatCode);
        konamiCheatSteps.aStageRemains();
    }

}
