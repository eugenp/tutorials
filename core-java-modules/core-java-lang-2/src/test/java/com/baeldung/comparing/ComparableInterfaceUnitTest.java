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
        Person.PersonWithEqualsAndWrongComparable richard = new Person.PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndWrongComparable joe = new Person.PersonWithEqualsAndWrongComparable("Joe", "Portman");

        assertThat(richard.compareTo(joe)).isNegative();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndSameLastNames_whenReversedCompareTo_thenZero() {
        Person.PersonWithEqualsAndWrongComparable richard = new Person.PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndWrongComparable mike = new Person.PersonWithEqualsAndWrongComparable("Mike", "Jefferson");

        assertThat(richard.compareTo(mike)).isZero();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndConsecutiveLastNames_whenReversedCompareTo_thenPositive() {
        Person.PersonWithEqualsAndWrongComparable richard = new Person.PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndWrongComparable joe = new Person.PersonWithEqualsAndWrongComparable("Joe", "Portman");

        assertThat(joe.compareTo(richard)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndWrongComparableAndSameLastNames_whenSortedSet_thenProblem() {
        Person.PersonWithEqualsAndWrongComparable richard = new Person.PersonWithEqualsAndWrongComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndWrongComparable mike = new Person.PersonWithEqualsAndWrongComparable("Mike", "Jefferson");

        SortedSet<Person.PersonWithEqualsAndWrongComparable> people = new TreeSet<>();
        people.add(richard);
        people.add(mike);

        assertThat(people).containsExactly(richard);
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndConsecutiveLastNames_whenCompareTo_thenNegative() {
        Person.PersonWithEqualsAndComparable richard = new Person.PersonWithEqualsAndComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparable joe = new Person.PersonWithEqualsAndComparable("Joe", "Portman");

        assertThat(richard.compareTo(joe)).isNegative();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndSameLastNames_whenReversedCompareTo_thenZero() {
        Person.PersonWithEqualsAndComparable richard = new Person.PersonWithEqualsAndComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparable mike = new Person.PersonWithEqualsAndComparable("Mike", "Jefferson");

        assertThat(richard.compareTo(mike)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndConsecutiveLastNames_whenReversedCompareTo_thenPositive() {
        Person.PersonWithEqualsAndComparable richard = new Person.PersonWithEqualsAndComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparable joe = new Person.PersonWithEqualsAndComparable("Joe", "Portman");

        assertThat(joe.compareTo(richard)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableAndSameLastNames_whenSortedSet_thenProblem() {
        Person.PersonWithEqualsAndComparable richard = new Person.PersonWithEqualsAndComparable("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparable mike = new Person.PersonWithEqualsAndComparable("Mike", "Jefferson");

        SortedSet<Person.PersonWithEqualsAndComparable> people = new TreeSet<>();
        people.add(richard);
        people.add(mike);

        assertThat(people).containsExactly(mike, richard);
    }
}
