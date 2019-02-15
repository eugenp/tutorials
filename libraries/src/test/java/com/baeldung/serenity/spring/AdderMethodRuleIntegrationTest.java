package com.baeldung.serenity.spring;

import com.baeldung.serenity.spring.steps.AdderSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule;
import net.thucydides.core.annotations.Steps;
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
@ContextConfiguration(locations = "classpath:adder-beans.xml")
public class AdderMethodRuleIntegrationTest {

    private static Logger LOG = LoggerFactory.getLogger(AdderMethodRuleIntegrationTest.class);

    @BeforeClass
    public static void initClass() {
        LOG.info("static adder before test class: {}", staticAdder);
    }

    @AfterClass
    public static void destroyClass() {
        LOG.info("static adder after test class: {}", staticAdder);
    }

    @Before
    public void init() {
        LOG.info("adder before test: {}", adder);
        staticAdder = adder;
    }

    @After
    public void destroy() {
        LOG.info("adder after test: {}", adder);
    }

    @Rule
    public SpringIntegrationMethodRule springMethodIntegration = new SpringIntegrationMethodRule();

    @Steps
    private AdderSteps adderSteps;

    @Value("#{props['adder']}")
    private int adder;

    private static int staticAdder;

    @Test
    public void givenNumber_whenAdd_thenSummedUp() {
        adderSteps.givenNumber();
        adderSteps.whenAdd(adder);
        adderSteps.thenSummedUp();
    }

}
