package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

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
        PersonWithEquals nathalie = new PersonWithEquals("Nathalie", "Portman");

        assertThat(Objects.equals(joe, nathalie)).isFalse();
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
