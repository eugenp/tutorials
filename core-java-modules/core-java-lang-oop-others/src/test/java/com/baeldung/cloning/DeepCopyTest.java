package com.baeldung.cloning;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class DeepCopyTest {

    @Test
    public void whenOriginalObjectModified_thenCopyDoesNotChange() {
        Address address = new Address("UP", "Agra", "India");
        Person originalPerson = new Person("Taj", "Mahal", address);
        Person deepCopyPerson = new Person(originalPerson);

        address.setState("Uttar Pradesh");
        assertNotEquals(deepCopyPerson.getAddress()
                .getState(), originalPerson.getAddress()
                .getState());
    }

    @Test
    public void whenOriginalObjectModified_thenClonedCopyDoesNotChange() {

        Address address = new Address("UP", "Agra", "India");
        Person originalPerson = new Person("Taj", "Mahal", address);
        Person deepCopyPerson = (Person) originalPerson.clone();

        address.setState("Uttar Pradesh");
        assertNotEquals(deepCopyPerson.getAddress()
                .getState(), originalPerson.getAddress()
                .getState());
    }

    @Test
    public void whenOriginalObjectModified_thenSerializedCopyDoesNotChange() throws IOException, ClassNotFoundException {
        Address address = new Address("UP", "Agra", "India");
        Person originalPerson = new Person("Taj", "Mahal", address);
        Person deepCopyPerson = (Person) originalPerson.deepCopy();

        address.setState("Uttar Pradesh");
        assertNotEquals(deepCopyPerson.getAddress()
                .getState(), originalPerson.getAddress()
                .getState());
    }

}
