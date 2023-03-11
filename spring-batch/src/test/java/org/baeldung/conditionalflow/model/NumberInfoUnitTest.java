package org.baeldung.conditionalflow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.baeldung.conditionalflow.config.NumberInfoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@EnableAutoConfiguration
@ContextConfiguration(classes = { NumberInfoConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class NumberInfoUnitTest {

    @Test
    void givenPositive_whenFrom_isPositive() {
        assertTrue(NumberInfo.from(1)
            .isPositive());
        assertTrue(NumberInfo.from(11)
            .isPositive());
        assertFalse(NumberInfo.from(0)
            .isPositive());
    }

    @Test
    void givenNegative_whenFrom_isNegative() {
        assertFalse(NumberInfo.from(-1)
            .isPositive());
        assertFalse(NumberInfo.from(-10)
            .isPositive());
    }

    @Test
    void givenEven_whenFrom_isEven() {
        assertTrue(NumberInfo.from(0)
            .isEven());
        assertTrue(NumberInfo.from(-2)
            .isEven());
        assertTrue(NumberInfo.from(2)
            .isEven());
        assertTrue(NumberInfo.from(-22)
            .isEven());
        assertTrue(NumberInfo.from(22)
            .isEven());
    }

    @Test
    void givenOdd_whenFrom_isOdd() {

        assertFalse(NumberInfo.from(1)
            .isEven());
        assertFalse(NumberInfo.from(-1)
            .isEven());

        assertFalse(NumberInfo.from(13)
            .isEven());
        assertFalse(NumberInfo.from(-13)
            .isEven());
        assertFalse(NumberInfo.from(31)
            .isEven());
        assertFalse(NumberInfo.from(-51)
            .isEven());
    }

    @Test
    void giveGeneratedInt_whenFrom_isNumberFromGenerator() {
        for (int i = -100; i < 100; i++) {
            assertEquals(i, NumberInfo.from(i)
                .getNumber());
        }
    }
}