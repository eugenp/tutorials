package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class ComparableInterfaceUnitTest {

    @Test
    void givenTwoConsecutiveStrings_whenCompareTo_thenNegative() {
        String first = "Google";
        String second = "Microsoft";

        assertThat(first.compareTo(second)).isNegative();
    }

    @Test
    void givenTwoEqualsStrings_whenCompareTo_thenZero() {
        String first = "Google";
        String second = "Google";

        assertThat(first.compareTo(second)).isZero();
    }

    @Test
    void givenTwoConsecutiveStrings_whenReversedCompareTo_thenPositive() {
        String first = "Google";
        String second = "Microsoft";

        assertThat(second.compareTo(first)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndConsecutiveLastNames_whenCompareTo_thenNegative() {
        PersonWithEqualsAndWrongComparable richard = new PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        PersonWithEqualsAndWrongComparable joe = new PersonWithEqualsAndWrongComparable("Joe", "Portman");

        assertThat(richard.compareTo(joe)).isNegative();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndSameLastNames_whenReversedCompareTo_thenZero() {
        PersonWithEqualsAndWrongComparable richard = new PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        PersonWithEqualsAndWrongComparable mike = new PersonWithEqualsAndWrongComparable("Mike", "Jefferson");

        assertThat(richard.compareTo(mike)).isZero();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndConsecutiveLastNames_whenReversedCompareTo_thenPositive() {
        PersonWithEqualsAndWrongComparable richard = new PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        PersonWithEqualsAndWrongComparable joe = new PersonWithEqualsAndWrongComparable("Joe", "Portman");

        assertThat(joe.compareTo(richard)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndSameLastNames_whenSortedSet_thenProblem() {
        PersonWithEqualsAndWrongComparable richard = new PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        PersonWithEqualsAndWrongComparable mike = new PersonWithEqualsAndWrongComparable("Mike", "Jefferson");

        SortedSet<PersonWithEqualsAndWrongComparable> people = new TreeSet<>();
        people.add(richard);
        people.add(mike);

        assertThat(people).containsExactly(richard);
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndConsecutiveLastNames_whenCompareTo_thenNegative() {
        PersonWithEqualsAndComparable richard = new PersonWithEqualsAndComparable("Richard", "Jefferson");
        PersonWithEqualsAndComparable joe = new PersonWithEqualsAndComparable("Joe", "Portman");

        assertThat(richard.compareTo(joe)).isNegative();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndSameLastNames_whenReversedCompareTo_thenZero() {
        PersonWithEqualsAndComparable richard = new PersonWithEqualsAndComparable("Richard", "Jefferson");
        PersonWithEqualsAndComparable mike = new PersonWithEqualsAndComparable("Mike", "Jefferson");

        assertThat(richard.compareTo(mike)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndConsecutiveLastNames_whenReversedCompareTo_thenPositive() {
        PersonWithEqualsAndComparable richard = new PersonWithEqualsAndComparable("Richard", "Jefferson");
        PersonWithEqualsAndComparable joe = new PersonWithEqualsAndComparable("Joe", "Portman");

        assertThat(joe.compareTo(richard)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndSameLastNames_whenSortedSet_thenProblem() {
        PersonWithEqualsAndComparable richard = new PersonWithEqualsAndComparable("Richard", "Jefferson");
        PersonWithEqualsAndComparable mike = new PersonWithEqualsAndComparable("Mike", "Jefferson");

        SortedSet<PersonWithEqualsAndComparable> people = new TreeSet<>();
        people.add(richard);
        people.add(mike);

        assertThat(people).containsExactly(mike, richard);
    }
}
