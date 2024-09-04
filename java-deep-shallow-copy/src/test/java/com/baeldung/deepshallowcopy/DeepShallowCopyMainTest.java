package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DeepShallowCopyMainTest {

    @Test
    void testShallowCopy() throws CloneNotSupportedException {
        Address address1 = new Address("123 Main St");
        Person person1 = new Person("Aniket", address1);
        Person person2 = (Person) person1.clone();

        assertNotNull(person1);
        assertNotNull(person2);
        assertEquals(person1.getName(), person2.getName());
        assertEquals(person1.getAddress().getStreet(), person2.getAddress().getStreet());

        // Modify address of person2
        person2.getAddress().setStreet("456 Elm St");
        assertEquals(person1.getAddress().getStreet(), person2.getAddress().getStreet());
        assertEquals("456 Elm St", person1.getAddress().getStreet());
    }

    @Test
    void testDeepCopy() {
        Address address1 = new Address("123 Main St");
        Person person1 = new Person("Aniket", address1);
        Person person3 = person1.deepCopy();

        assertNotNull(person1);
        assertNotNull(person3);
        assertEquals(person1.getName(), person3.getName());
        assertEquals(person1.getAddress().getStreet(), person3.getAddress().getStreet());

        // Modify address of person3
        person3.getAddress().setStreet("789 Oak St");
        assertNotEquals(person1.getAddress().getStreet(), person3.getAddress().getStreet());
        assertEquals("123 Main St", person1.getAddress().getStreet());
    }

    @Test
    void testImmutableObject() {
        Address address1 = new Address("123 Main St");
        ImmutablePerson immutablePerson1 = new ImmutablePerson("Aniket", address1);
        ImmutablePerson immutablePerson2 = new ImmutablePerson("Aniket", new Address("456 Elm St"));

        assertNotNull(immutablePerson1);
        assertNotNull(immutablePerson2);
        assertEquals("Aniket", immutablePerson1.getName());
        assertEquals("123 Main St", immutablePerson1.getAddress().getStreet());
        assertEquals("Aniket", immutablePerson2.getName());
        assertEquals("456 Elm St", immutablePerson2.getAddress().getStreet());

        // Modify original address and verify it does not affect immutablePerson1
        address1.setStreet("101 Maple St");
        assertNotEquals("101 Maple St", immutablePerson1.getAddress().getStreet());
        assertEquals("123 Main St", immutablePerson1.getAddress().getStreet());
    }
}

