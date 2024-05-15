package com.baeldung.comparing;

import java.time.LocalDate;
import java.util.Objects;

public class PersonWithEqualsAndComparable implements Comparable<PersonWithEqualsAndComparable> {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public PersonWithEqualsAndComparable(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new NullPointerException("Names can't be null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonWithEqualsAndComparable(String firstName, String lastName, LocalDate birthDate) {
        this(firstName, lastName);

        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonWithEqualsAndComparable that = (PersonWithEqualsAndComparable) o;
        return firstName.equals(that.firstName) &&
          lastName.equals(that.lastName) &&
          Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public int compareTo(PersonWithEqualsAndComparable o) {
        int lastNamesComparison = this.lastName.compareTo(o.lastName);
        if (lastNamesComparison == 0) {
            int firstNamesComparison = this.firstName.compareTo(o.firstName);
            if (firstNamesComparison == 0) {
                if (this.birthDate != null && o.birthDate != null) {
                    return this.birthDate.compareTo(o.birthDate);
                } else if (this.birthDate != null) {
                  return 1;
                } else if (o.birthDate != null) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                return firstNamesComparison;
            }
        } else {
            return lastNamesComparison;
        }
    }
}
