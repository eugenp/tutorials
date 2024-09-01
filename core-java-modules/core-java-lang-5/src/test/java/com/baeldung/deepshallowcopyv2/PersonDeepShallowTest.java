package com.baeldung.deepshallowcopyv2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonDeepShallowTest {

    @Test
    void givenPerson_whenShallowClone_thenAddressIsShared() throws CloneNotSupportedException {
        Address address = new Address("New York", "NY");
        Person person1 = new Person("John", address);

        // Shallow Copy
        Person person2 = (Person) person1.clone();

        // Modify the address in person2
        person2.getAddress().setCity("Los Angeles");

        // person1 and person2 should share the same Address object
        assertEquals(person1.getAddress().getCity(), person2.getAddress().getCity());
    }

    @Test
    void givenPerson_whenDeepClone_thenAddressIsDifferent() throws CloneNotSupportedException {
        Address address = new Address("New York", "NY");
        Person person1 = new Person("John", address);

        // Deep Copy
        Person person3 = person1.deepClone();

        // Modify the address in person3
        person3.getAddress().setCity("Chicago");

        // person1 and person3 should have different Address objects
        assertNotEquals(person1.getAddress().getCity(), person3.getAddress().getCity());
    }
}
