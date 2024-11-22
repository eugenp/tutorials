package com.baeldung.comparing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class ObjectsEqualsStaticMethodUnitTest {

    @Test
    void givenTwoPersonWithEqualsWithSameNames_whenObjectsEquals_thenTrue() {
        PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");
        PersonWithEquals joeAgain = new PersonWithEquals("Joe", "Portman");

        assertThat(Objects.equals(joe, joeAgain)).isTrue();
    }

    @Test
    void givenTwoPersonWithEqualsWithDifferentNames_whenObjectsEquals_thenFalse() {
        PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");
        PersonWithEquals natalie = new PersonWithEquals("Natalie", "Portman");

        assertThat(Objects.equals(joe, natalie)).isFalse();
    }

    @Test
    void givenTwoPersonWithEqualsFirstNull_whenObjectsEquals_thenFalse() {
        PersonWithEquals nobody = null;
        PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");

        assertThat(Objects.equals(nobody, joe)).isFalse();
    }

    @Test
    void givenTwoObjectsSecondtNull_whenObjectsEquals_thenFalse() {
        PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");
        PersonWithEquals nobody = null;

        assertThat(Objects.equals(joe, nobody)).isFalse();
    }

    @Test
    void givenTwoObjectsNull_whenObjectsEquals_thenTrue() {
        PersonWithEquals nobody = null;
        PersonWithEquals nobodyAgain = null;

        assertThat(Objects.equals(nobody, nobodyAgain)).isTrue();
    }
}
