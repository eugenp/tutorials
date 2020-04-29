package com.baeldung.comparing;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class Person {
    public static class PersonWithoutEquals {
        private String firstName;
        private String lastName;

        public PersonWithoutEquals(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public static class PersonWithEquals {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;

        public PersonWithEquals(String firstName, String lastName) {
            if (firstName == null || lastName == null) {
                throw new NullPointerException("Names can't be null");
            }
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public PersonWithEquals(String firstName, String lastName, LocalDate birthDate) {
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
            PersonWithEquals that = (PersonWithEquals) o;
            return firstName.equals(that.firstName) &&
              lastName.equals(that.lastName) &&
              Objects.equals(birthDate, that.birthDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName);
        }
    }

    public static class PersonWithEqualsAndWrongComparable implements Comparable<PersonWithEqualsAndWrongComparable> {
        private String firstName;
        private String lastName;
        private LocalDate birthDate;

        public PersonWithEqualsAndWrongComparable(String firstName, String lastName) {
            if (firstName == null || lastName == null) {
                throw new NullPointerException("Names can't be null");
            }
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public PersonWithEqualsAndWrongComparable(String firstName, String lastName, LocalDate birthDate) {
            this(firstName, lastName);

            this.birthDate = birthDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonWithEqualsAndWrongComparable that = (PersonWithEqualsAndWrongComparable) o;
            return firstName.equals(that.firstName) &&
              lastName.equals(that.lastName) &&
              Objects.equals(birthDate, that.birthDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName);
        }

        @Override
        public int compareTo(PersonWithEqualsAndWrongComparable o) {
            return this.lastName.compareTo(o.lastName);
        }
    }

    public static class PersonWithEqualsAndComparable implements Comparable<PersonWithEqualsAndComparable> {
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

    public static class PersonWithEqualsAndComparableUsingComparator implements Comparable<PersonWithEqualsAndComparableUsingComparator> {
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
}
