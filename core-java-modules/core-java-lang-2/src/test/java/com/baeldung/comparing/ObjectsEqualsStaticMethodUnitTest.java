package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectsEqualsStaticMethodUnitTest {

    @Test
    void givenTwoPersonWithEqualsWithSameNames_whenObjectsEquals_thenTrue() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals joeAgain = new Person.PersonWithEquals("Joe", "Portman");

        assertThat(Objects.equals(joe, joeAgain)).isTrue();
    }

    @Test
    void givenTwoPersonWithEqualsWithDifferentNames_whenObjectsEquals_thenFalse() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals nathalie = new Person.PersonWithEquals("Nathalie", "Portman");

        assertThat(Objects.equals(joe, nathalie)).isFalse();
    }

    @Test
    void givenTwoPersonWithEqualsFirstNull_whenObjectsEquals_thenFalse() {
        Person.PersonWithEquals nobody = null;
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");

        assertThat(Objects.equals(nobody, joe)).isFalse();
    }

    @Test
    void givenTwoObjectsSecondtNull_whenObjectsEquals_thenFalse() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals nobody = null;

        assertThat(Objects.equals(joe, nobody)).isFalse();
    }

    @Test
    void givenTwoObjectsNull_whenObjectsEquals_thenTrue() {
        Person.PersonWithEquals nobody = null;
        Person.PersonWithEquals nobodyAgain = null;

        assertThat(Objects.equals(nobody, nobodyAgain)).isTrue();
    }
}
