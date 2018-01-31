package com.baeldung.testing.assertj.custom;

import static com.baeldung.testing.assertj.custom.PersonAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertJPersonAssertUnitTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenPersonNameMatches_thenCorrect() {
        Person person = new Person("John Doe", 20);
        assertThat(person).hasFullName("John Doe");
    }

    @Test
    public void whenPersonAgeLessThanEighteen_thenNotAdult() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected adult but was juvenile");
        Person person = new Person("Jane Roe", 16);
        assertThat(person).isAdult();
    }
}
