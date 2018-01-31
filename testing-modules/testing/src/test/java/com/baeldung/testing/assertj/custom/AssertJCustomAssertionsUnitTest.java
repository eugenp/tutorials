package com.baeldung.testing.assertj.custom;

import static com.baeldung.testing.assertj.custom.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertJCustomAssertionsUnitTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void whenPersonDoesNotHaveAMatchingNickname_thenIncorrect() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected nickname John but did not have");
        Person person = new Person("John Doe", 20);
        person.addNickname("Nick");
        assertThat(person).hasNickname("John");
    }

    @Test
    public void whenCarIsUsed_thenCorrect() {
        Person person = new Person("Jane Roe", 16);
        Car car = new Car("SUV");
        car.setOwner(person);
        assertThat(car).isUsed();
    }
}
