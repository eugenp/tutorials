package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.AdderServiceSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationClassRule;
import net.thucydides.core.annotations.Steps;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static com.baeldung.serenity.spring.RandomNumberUtil.randomInt;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AdderClassDirtiesContextIntegrationTest.DirtiesContextIntegrationTest.class, AdderClassDirtiesContextIntegrationTest.AnotherDirtiesContextIntegrationTest.class })
public class AdderClassDirtiesContextIntegrationTest {

    @RunWith(SerenityRunner.class)
    @ContextConfiguration(classes = AdderService.class)
    public static abstract class Base {

        @Steps
        AdderServiceSteps adderServiceSteps;

        @ClassRule
        public static SpringIntegrationClassRule springIntegrationClassRule = new SpringIntegrationClassRule();

        void whenAccumulate_thenSummedUp() {
            adderServiceSteps.whenAccumulate();
            adderServiceSteps.summedUp();
        }

        void whenAdd_thenSumWrong() {
            adderServiceSteps.whenAdd();
            adderServiceSteps.sumWrong();
        }

        void whenAdd_thenSummedUp() {
            adderServiceSteps.whenAdd();
            adderServiceSteps.summedUp();
        }

    }

    @DirtiesContext(classMode = AFTER_CLASS)
    public static class AnotherDirtiesContextIntegrationTest extends Base {

        @Test
        public void givenNumber_whenAdd_thenSumWrong() {
            super.whenAdd_thenSummedUp(); // expecting zero
            adderServiceSteps.givenBaseAndAdder(randomInt(), randomInt());
            super.whenAccumulate_thenSummedUp();
            super.whenAdd_thenSumWrong();
        }
    }

    @DirtiesContext(classMode = AFTER_CLASS)
    public static class DirtiesContextIntegrationTest extends Base {

        @Test
        public void givenNumber_whenAdd_thenSumWrong() {
            super.whenAdd_thenSummedUp(); // expecting zero
            adderServiceSteps.givenBaseAndAdder(randomInt(), randomInt());
            super.whenAccumulate_thenSummedUp();
            super.whenAdd_thenSumWrong();
        }
    }

}
