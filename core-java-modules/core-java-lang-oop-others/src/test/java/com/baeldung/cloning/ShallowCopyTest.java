package com.baeldung.cloning;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ShallowCopyTest {

    @Test
    public void whenOriginalObjectModified_ThenCopyChanges() {
        Address address = new Address("UP", "Agra", "India");
        Person originalPerson = new Person("Taj", "Mahal", address);
        Person shallowCopyPerson = new Person(originalPerson.getFirstName(), originalPerson.getLastName(), originalPerson.getAddress());
        address.setState("Uttar Pradesh");
        assertEquals(shallowCopyPerson.getAddress()
                .getState(), originalPerson.getAddress()
                .getState());
    }

}
