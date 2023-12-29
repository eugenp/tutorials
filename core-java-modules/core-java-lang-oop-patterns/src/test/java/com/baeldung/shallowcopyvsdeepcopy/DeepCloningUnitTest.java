package com.baeldung.shallowcopyvsdeepcopy;

import org.apache.commons.lang3.SerializationUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeepCloningUnitTest {

    @Test
    void givenPersonWithAddress_shouldClonePersonAndItsAddressAdIndependentObjectUsingCloneMethod() {
        String addressValue = "address 1";
        PersonForDeepCloning person = new PersonForDeepCloning();
        PersonForDeepCloning.Address address = new PersonForDeepCloning.Address();
        address.setValue(addressValue);
        person.setAddress(address);

        PersonForDeepCloning clonedPerson = person.clone();
        Assertions.assertThat(clonedPerson)
            .extracting("address")
            .isNotEqualTo(person.getAddress())
            .extracting("value")
            .isEqualTo(addressValue);

        clonedPerson.getAddress()
            .setValue("changed address");

        Assertions.assertThat(person)
            .extracting("address")
            .extracting("value")
            .isEqualTo(addressValue);

        Assertions.assertThat(clonedPerson)
            .extracting("address")
            .extracting("value")
            .isNotEqualTo(addressValue);
    }

    @Test
    void givenPersonWithAddress_shouldClonePersonAndItsAddressAdIndependentObjectUsingSerializationUtils() {
        String addressValue = "address 1";
        PersonForDeepCloning person = new PersonForDeepCloning();
        PersonForDeepCloning.Address address = new PersonForDeepCloning.Address();
        address.setValue(addressValue);
        person.setAddress(address);

        PersonForDeepCloning clonedPerson = SerializationUtils.clone(person);
        Assertions.assertThat(clonedPerson)
            .extracting("address")
            .isNotEqualTo(person.getAddress())
            .extracting("value")
            .isEqualTo(addressValue);

        clonedPerson.getAddress()
            .setValue("changed address");

        Assertions.assertThat(person)
            .extracting("address")
            .extracting("value")
            .isEqualTo(addressValue);

        Assertions.assertThat(clonedPerson)
            .extracting("address")
            .extracting("value")
            .isNotEqualTo(addressValue);
    }
}
