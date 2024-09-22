package com.baeldung.serenity.spring;

import static com.baeldung.serenity.spring.RandomNumberUtil.randomInt;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.serenity.spring.steps.AdderServiceSteps;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

/**
 * @author aiet
 */
@RunWith(SerenityRunner.class)
public class AdderServiceIntegrationTest {

    @Steps
    private AdderServiceSteps adderServiceSteps;

    @Test
    public void givenNumber_whenAdd_thenSummedUp() {
        adderServiceSteps.givenBaseAndAdder(randomInt(), randomInt());
        adderServiceSteps.whenAdd();
        adderServiceSteps.summedUp();
    }

}
