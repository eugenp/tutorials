package com.baeldung.comparing;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class PersonWithEqualsAndComparableUsingComparator implements Comparable<PersonWithEqualsAndComparableUsingComparator> {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public PersonWithEqualsAndComparableUsingComparator(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new NullPointerException("Names can't be null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonWithEqualsAndComparableUsingComparator(String firstName, String lastName, LocalDate birthDate) {
        this(firstName, lastName);

        this.birthDate = birthDate;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonWithEqualsAndComparableUsingComparator that = (PersonWithEqualsAndComparableUsingComparator) o;
        return firstName.equals(that.firstName) &&
          lastName.equals(that.lastName) &&
          Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public int compareTo(PersonWithEqualsAndComparableUsingComparator o) {
        return Comparator.comparing(PersonWithEqualsAndComparableUsingComparator::lastName)
          .thenComparing(PersonWithEqualsAndComparableUsingComparator::firstName)
          .thenComparing(PersonWithEqualsAndComparableUsingComparator::birthDate, Comparator.nullsLast(Comparator.naturalOrder()))
          .compare(this, o);
    }
}
