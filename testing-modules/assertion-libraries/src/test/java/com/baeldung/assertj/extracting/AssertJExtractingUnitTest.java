package com.baeldung.assertj.extracting;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class AssertJExtractingUnitTest {
    static final List<Address> RESTRICTED_ADDRESSES = new ArrayList<>();

    @Test
    void whenUsingRegularAssertionFlow_thenCorrect() {

        // Given
        Person person = new Person("aName", "aLastName", new Address("aStreet", "aCity", new ZipCode(90210)));

        // Then
        Address address = person.getAddress();
        assertThat(address).isNotNull()
          .isNotIn(RESTRICTED_ADDRESSES);
        ZipCode zipCode = address.getZipCode();
        assertThat(zipCode).isNotNull();
        assertThat(zipCode.getZipcode()).isBetween(1000L, 100_000L);
    }

    @Test
    void whenUsingExtractingAssertionFlow_thenCorrect() {

        // Given
        Person person = new Person("aName", "aLastName", new Address("aStreet", "aCity", new ZipCode(90210)));

        // Then
        assertThat(person)
          .extracting(Person::getAddress)
          .isNotNull()
          .isNotIn(RESTRICTED_ADDRESSES)
          .extracting(Address::getZipCode)
          .isNotNull()
          .extracting(ZipCode::getZipcode, as(InstanceOfAssertFactories.LONG))
          .isBetween(1_000L, 100_000L);
    }
}
