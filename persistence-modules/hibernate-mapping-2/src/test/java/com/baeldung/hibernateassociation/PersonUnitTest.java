package com.baeldung.hibernateassociation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PersonUnitTest {

    @Test
    public void whenCreatingPerson_thenPersonIsCreatedWithCorrectFields() {
   
        int personId = 1;
        String name = "John Doe";
        int addressId = 2;
        String city = "New York";
        Address address = new Address(addressId, city);

        Person person = new Person(personId, name, address);

        assertEquals(personId, person.getPersonId());
        assertEquals(name, person.getName());
        assertEquals(address, person.getAddress());
    }
}
