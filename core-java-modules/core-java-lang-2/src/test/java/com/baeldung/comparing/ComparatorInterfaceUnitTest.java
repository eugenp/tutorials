package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorInterfaceUnitTest {

    @Test
    void givenListOfTwoPersonWithEqualsAndComparatorByFirstName_whenSort_thenSortedByFirstNames() {
        PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");
        PersonWithEquals allan = new PersonWithEquals("Allan", "Dale");

        List<PersonWithEquals> people = new ArrayList<>();
        people.add(joe);
        people.add(allan);

        Comparator<PersonWithEquals> compareByFirstNames = new Comparator<PersonWithEquals>() {
            @Override
            public int compare(PersonWithEquals o1, PersonWithEquals o2) {
                return o1.firstName().compareTo(o2.firstName());
            }
        };
        people.sort(compareByFirstNames);

        assertThat(people).containsExactly(allan, joe);
    }

    @Test
    void givenListOfTwoPersonWithEqualsAndComparatorByFirstNameFunctionalStyle_whenSort_thenSortedByFirstNames() {
        PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");
        PersonWithEquals allan = new PersonWithEquals("Allan", "Dale");

        List<PersonWithEquals> people = new ArrayList<>();
        people.add(joe);
        people.add(allan);

        Comparator<PersonWithEquals> compareByFirstNames = Comparator.comparing(PersonWithEquals::firstName);
        people.sort(compareByFirstNames);

        assertThat(people).containsExactly(allan, joe);
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndConsecutiveLastNames_whenCompareTo_thenNegative() {
        PersonWithEqualsAndComparableUsingComparator richard = new PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        PersonWithEqualsAndComparableUsingComparator joe = new PersonWithEqualsAndComparableUsingComparator("Joe", "Portman");

        assertThat(richard.compareTo(joe)).isNegative();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndSameLastNames_whenReversedCompareTo_thenZero() {
        PersonWithEqualsAndComparableUsingComparator richard = new PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        PersonWithEqualsAndComparableUsingComparator mike = new PersonWithEqualsAndComparableUsingComparator("Mike", "Jefferson");

        assertThat(richard.compareTo(mike)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndConsecutiveLastNames_whenReversedCompareTo_thenPositive() {
        PersonWithEqualsAndComparableUsingComparator richard = new PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        PersonWithEqualsAndComparableUsingComparator joe = new PersonWithEqualsAndComparableUsingComparator("Joe", "Portman");

        assertThat(joe.compareTo(richard)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndSameLastNames_whenSortedSet_thenProblem() {
        PersonWithEqualsAndComparableUsingComparator richard = new PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        PersonWithEqualsAndComparableUsingComparator mike = new PersonWithEqualsAndComparableUsingComparator("Mike", "Jefferson");

        SortedSet<PersonWithEqualsAndComparableUsingComparator> people = new TreeSet<>();
        people.add(richard);
        people.add(mike);

        assertThat(people).containsExactly(mike, richard);
    }
}
