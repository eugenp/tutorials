package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.KonamiCodeRestSteps;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author aiet
 */
@RunWith(SerenityRunner.class)
public class KonamiCodeMockMvcTest {

    @Before
    public void init() {
        RestAssuredMockMvc.standaloneSetup(new KonamiCodeController());
    }

    @Steps KonamiCodeRestSteps steps;

    @Test
    public void givenOfficialClassicCheatcode_whenCheat_ThenTheCodeShouldDoTheTrick() throws Exception {
        steps.givenClassicCheatCode();
        steps.whenCheat();
        steps.thenClassicCodeCanDoTheTrick();
    }

}
