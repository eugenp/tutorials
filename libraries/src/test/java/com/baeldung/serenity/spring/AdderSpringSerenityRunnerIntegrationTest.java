package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.AdderSteps;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(SpringIntegrationSerenityRunner.class)
@ContextConfiguration(locations = "classpath:adder-beans.xml")
public class AdderSpringSerenityRunnerIntegrationTest {

    @Steps
    private AdderSteps adderSteps;

    @Value("#{props['adder']}")
    private int adder;

    @Test
    public void givenNumber_whenAdd_thenSummedUp() {
        adderSteps.givenNumber();
        adderSteps.whenAdd(adder);
        adderSteps.thenSummedUp();
    }

}
