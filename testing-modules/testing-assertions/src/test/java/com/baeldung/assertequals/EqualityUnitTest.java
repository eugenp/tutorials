package com.baeldung.assertequals;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EqualityUnitTest {

    @Test
    void givenPersons_whenUseRecursiveComparison_thenOk() {
        Person expected = new Person(1L, "Jane", "Doe");
        Address address1 = new Address(1L, "New York", "Sesame Street", "United States");
        expected.setAddress(address1);

        Person actual = new Person(1L, "Jane", "Doe");
        Address address2 = new Address(1L, "New York", "Sesame Street", "United States");
        actual.setAddress(address2);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenPersons_whenUseRecursiveComparisonIgnoringField_thenOk() {
        Person expected = new Person(1L, "Jane", "Doe");
        Person actual = new Person(2L, "Jane", "Doe");

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void givenPersons_whenCheckForSamePropertyValues_thenReturnOk() {
        Person expected = new Person(1L, "Jane", "Doe");
        Address address1 = new Address(1L, "New York", "Sesame Street", "United States");
        expected.setAddress(address1);

        Person actual = new Person(1L, "Jane", "Doe");
        Address address2 = new Address(1L, "New York", "Sesame Street", "United States");
        actual.setAddress(address2);

        MatcherAssert.assertThat(actual, samePropertyValuesAs(expected, "address"));
        MatcherAssert.assertThat(actual.getAddress(), samePropertyValuesAs(expected.getAddress()));
    }

    @Test
    void givenPerson_whenReflectionToStringBuilder_thenReturnOk() {
        Person expected = new Person(1L, "Jane", "Doe");
        Address address1 = new Address(1L, "New York", "Sesame Street", "United States");
        expected.setAddress(address1);

        Person actual = new Person(1L, "Jane", "Doe");
        Address address2 = new Address(1L, "New York", "Sesame Street", "United States");
        actual.setAddress(address2);

        assertThat(ReflectionToStringBuilder.toString(actual, ToStringStyle.SHORT_PREFIX_STYLE))
                .isEqualTo(ReflectionToStringBuilder.toString(expected, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    void givenPersons_whenEqualsBuilder_thenReturnTrue() {
        Person expected = new Person(1L, "Jane", "Doe");
        Address address1 = new Address(1L, "New York", "Sesame Street", "United States");
        expected.setAddress(address1);

        Person actual = new Person(1L, "Jane", "Doe");
        Address address2 = new Address(1L, "New York", "Sesame Street", "United States");
        actual.setAddress(address2);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "address"));
        assertTrue(EqualsBuilder.reflectionEquals(expected.getAddress(), actual.getAddress()));
    }

    @Test
    void givenPersons_whenReflectionEquals_thenReturnOk() {
        Person expected = new Person(1L, "Jane", "Doe");
        Address address1 = new Address(1L, "New York", "Sesame Street", "United States");
        expected.setAddress(address1);

        Person actual = new Person(1L, "Jane", "Doe");
        Address address2 = new Address(1L, "New York", "Sesame Street", "United States");
        actual.setAddress(address2);

        assertTrue(new ReflectionEquals(expected, "address").matches(actual));
        assertTrue(new ReflectionEquals(expected.getAddress()).matches(actual.getAddress()));
    }

}
