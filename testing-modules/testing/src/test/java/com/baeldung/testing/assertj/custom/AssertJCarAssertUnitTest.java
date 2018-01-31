package com.baeldung.testing.assertj.custom;

import static com.baeldung.testing.assertj.custom.CarAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertJCarAssertUnitTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenCarTypeDoesNotMatch_thenIncorrect() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected type SUV but was Sedan");
        Car car = new Car("Sedan");
        assertThat(car).hasType("SUV");
    }
}
