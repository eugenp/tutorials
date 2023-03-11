package com.baeldung.javabasics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class CopyObjectUnitTest {

    @Test
    void testShallowCopy() throws CloneNotSupportedException {
        Address address = new Address("Mun", "Blr");
        Person person = new Person("Kajal", address);
        Person personCopy = person.clone();

        //true same object reference
        assertSame(personCopy.getAddress(), address);

        address.setStreet("Hsr");

        assertSame(personCopy.getAddress(), address);
    }

    @Test
    void testDeepCopy() throws CloneNotSupportedException {
        Address address = new Address("Mun", "Blr");
        Student student = new Student("Kajal", address);
        Student studentCopy = student.clone();

        //true different object reference
        assertNotSame(studentCopy.getAddress(), address);

    }
}
