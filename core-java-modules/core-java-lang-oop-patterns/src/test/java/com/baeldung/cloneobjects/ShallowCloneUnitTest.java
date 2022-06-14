package com.baeldung.cloneobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShallowCloneUnitTest {

    @Test
    void whenChangingTheFirstPersonAddressCity_thenTheAddressCitiesMatch() throws CloneNotSupportedException {

        Address address = new Address("Victoria Street", "New York", "USA");
        Contact contact = new Contact("+1234567890", "contact@baeldung.com");
        Person firstPerson = new Person("John Smith", 31, address, contact);
        Person secondPerson = (Person) firstPerson.clone();

        firstPerson.getAddress()
            .setCity("Los Angeles");

        assertEquals(firstPerson.getAddress()
            .getCity(),
            secondPerson.getAddress()
                .getCity());
    }
}
