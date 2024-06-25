package com.baeldung.copy;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {


    @Test
    public void testShallowClone() throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);
        Person person2 = (Person) person1.clone();

        System.out.println("Before modification:");
        System.out.println("Original Address: " + person1.address);
        System.out.println("Cloned Address: " + person2.address);

        assertNotSame(person1, person2); // Ensure person1 and person2 are different objects
        assertSame(person1.address, person2.address); // Ensure person1 and person2 share the same Address object
        assertEquals(person1.name, person2.name); // Ensure names are equal

        // Modify the address in person2 and check if it affects person1
        person2.address.city = "San Francisco";

        System.out.println("After modification:");
        System.out.println("Original Address City: " + person1.address.city);
        System.out.println("Cloned Address City: " + person2.address.city);

        assertEquals("San Francisco", person1.address.city); // Expecting change in person1's address
    }



    @Test
    public void testDeepClone() throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);
        Person person2 = person1.deepClone();

        System.out.println("Before modification:");
        System.out.println("Original Address: " + person1.address);
        System.out.println("Cloned Address: " + person2.address);

        assertNotSame(person1, person2); // Ensure person1 and person2 are different objects
        assertNotSame(person1.address, person2.address); // Ensure person1 and person2 do not share the same Address object
        assertEquals(person1.name, person2.name); // Ensure names are equal
        assertEquals(person1.address.city, person2.address.city); // Ensure addresses are equal

        // Modify the address in person2 and check it does not affect person1
        person2.address.city = "San Francisco";

        System.out.println("After modification:");
        System.out.println("Original Address City: " + person1.address.city);
        System.out.println("Cloned Address City: " + person2.address.city);

        assertNotEquals(person1.address.city, person2.address.city); // Expecting no change in person1's address
    }
}
