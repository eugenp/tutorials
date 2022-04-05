package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.AdderRestSteps;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.baeldung.serenity.spring.RandomNumberUtil.randomInt;

/**
 * @author aiet
 */
@RunWith(SerenityRunner.class)
public class AdderMockMvcIntegrationTest {

    @Before
    public void init() {
        RestAssuredMockMvc.standaloneSetup(new PlainAdderController());
    }

    @Steps
    AdderRestSteps steps;

    @Test
    public void givenNumber_whenAdd_thenSummedUp() throws Exception {
        steps.givenCurrentNumber();
        steps.whenAddNumber(randomInt());
        steps.thenSummedUp();
    }

}
