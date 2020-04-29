package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EqualsMethodUnitTest {

    @Test
    void givenTwoIntegersWithSameValue_whenEquals_thenTrue() {
        Integer a = new Integer(1);
        Integer b = new Integer(1);

        assertThat(a.equals(b)).isTrue();
    }

    @Test
    void givenTwoStringsWithSameValue_whenEquals_thenTrue() {
        String a = new String("Hello!");
        String b = new String("Hello!");

        assertThat(a.equals(b)).isTrue();
    }

    @Test
    void givenTwoStringsWithDifferentValue_whenEquals_thenFalse() {
        String a = new String("Hello!");
        String b = new String("Hello World!");

        assertThat(a.equals(b)).isFalse();
    }

    @Test
    void givenTwoObjectsFirstNull_whenEquals_thenNullPointerExceptionThrown() {
        Object a = null;
        Object b = new String("Hello!");

        assertThrows(NullPointerException.class, () -> a.equals(b));
    }

    @Test
    void givenTwoObjectsSecondNull_whenEquals_thenFalse() {
        Object a = new String("Hello!");
        Object b = null;

        assertThat(a.equals(b)).isFalse();
    }

    @Test
    void givenTwoPersonWithoutEqualsWithSameNames_whenEquals_thenFalse() {
        Person.PersonWithoutEquals joe = new Person.PersonWithoutEquals("Joe", "Portman");
        Person.PersonWithoutEquals joeAgain = new Person.PersonWithoutEquals("Joe", "Portman");

        assertThat(joe.equals(joeAgain)).isFalse();
    }

    @Test
    void givenTwoPersonWithEqualsWithSameNames_whenEquals_thenTrue() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals joeAgain = new Person.PersonWithEquals("Joe", "Portman");

        assertThat(joe.equals(joeAgain)).isTrue();
    }

    @Test
    void givenTwoPersonWittEqualsWithDifferentNames_whenEquals_thenFalse() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals nathalie = new Person.PersonWithEquals("Nathalie", "Portman");

        assertThat(joe.equals(nathalie)).isFalse();
    }
}
