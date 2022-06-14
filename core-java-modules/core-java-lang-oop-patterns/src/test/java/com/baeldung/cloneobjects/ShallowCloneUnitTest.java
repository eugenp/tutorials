package com.baeldung.cloneobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShallowCloneUnitTest {

    @Test
    public void whenChangingTheFirstPersonAddressCity_thenTheAddressCitiesMatch() throws CloneNotSupportedException {

        // creating the address
        Address address = new Address("Victoria Street", "New York", "USA");

        // creating the address
        Contact contact = new Contact("+1234567890", "contact@baeldung.com");

        // creating the firstPerson
        Person firstPerson = new Person("John Smith", 31, address, contact);

        // creating the secondPerson and assign to it a clone of the firstPerson
        Person secondPerson = (Person) firstPerson.clone();

        // firstPerson.Address and secondPerson.Address references the same Address object
        assertEquals(firstPerson.getAddress(), secondPerson.getAddress());

        // setting a new value for the firstPerson.Address.City
        firstPerson.getAddress()
            .setCity("Los Angeles");

        // we observe that the firstPerson.Address.City and secondPerson.Address.City are equal
        assertEquals(firstPerson.getAddress()
            .getCity(),
            secondPerson.getAddress()
                .getCity());
    }

}
