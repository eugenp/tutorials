package com.baeldung.shallowcopyvsdeepcopy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShallowCloningUnitTest {

    @Test
    void givenPersonWithNameAndAge_shouldCloneAllItsFieldsIntoNewInstanceManually() {
        Person person = new Person();
        person.setAge(20);
        person.setName("John");

        Person clonedPerson = new Person();
        clonedPerson.setAge(person.getAge());
        clonedPerson.setName(person.getName());

        Assertions.assertThat(clonedPerson)
            .extracting("name", "age")
            .containsExactly(person.getName(), person.getAge());
    }

    @Test
    void givenPersonWithNameAndAge_shouldCloneAllItsFieldsIntoNewInstanceUsingCloneMethod() {
        Person person = new Person();
        person.setAge(20);
        person.setName("John");

        Person clonedPerson = person.clone();
        Assertions.assertThat(clonedPerson)
            .extracting("name", "age")
            .containsExactly(person.getName(), person.getAge());
    }

    @Test
    void givenPersonWithAddress_shouldCloneOnlyTheAddressReferenceAndWillBeChangedInAllTheInstances() {
        String addressValue = "address 1";
        Person person = new Person();
        Person.Address address = new Person.Address();
        address.setValue(addressValue);
        person.setAddress(address);

        Person clonedPerson = person.clone();
        Assertions.assertThat(clonedPerson)
            .extracting("address")
            .isEqualTo(person.getAddress());

        clonedPerson.getAddress()
            .setValue("changed address");

        Assertions.assertThat(person)
            .extracting("address")
            .extracting("value")
            .isNotEqualTo(addressValue);

        Assertions.assertThat(clonedPerson)
            .extracting("address")
            .extracting("value")
            .isNotEqualTo(addressValue);
    }
}
