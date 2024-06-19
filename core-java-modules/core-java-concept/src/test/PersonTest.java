package com.baeldung.copy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void givenPerson_whenShallowCloned_thenAddressIsShared() throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);
        Person person2 = (Person) person1.clone();

        assertNotSame(person1, person2);
        assertSame(person1.address, person2.address);
        assertEquals(person1.name, person2.name);
        person2.address.city = "San Francisco";
        assertEquals(person1.address.city, "San Francisco");
    }

    @Test
    void givenPerson_whenDeepCloned_thenBothObjectsAreIndependent() throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);
        Person person2 = person1.deepClone();

        assertNotSame(person1, person2);
        assertNotSame(person1.address, person2.address);
        assertEquals(person1.name, person2.name);
        assertEquals(person1.address.city, person2.address.city);

        person2.address.city = "San Francisco";
        assertNotEquals(person1.address.city, person2.address.city);
    }
}
