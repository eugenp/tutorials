package com.baeldung;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        Address address = new Address("New York", "5th Avenue");
        User original = new User("John", 30, "john@example.com", address);
        User deepCopy = (User) original.clone();

        // Verify the original and the deep copy are not the same object
        assertNotSame(original, deepCopy);

        // Verify the mutable Address field is deeply copied
        assertNotSame(original.address, deepCopy.address);

        // Verify the contents of the deep copy are equal to the original
        assertEquals(original, deepCopy);

        // Modify the mutable Address field in the deep copy
        deepCopy.address.city = "Los Angeles";

        // Verify that the original object is not affected by changes in the deep copy
        assertNotEquals(original.address.city, deepCopy.address.city);
    }

    @Test
    public void testShallowCopy() {
        Address address = new Address("New York", "5th Avenue");
        User original = new User("John", 30, "john@example.com", address);

        // Perform a shallow copy manually
        User shallowCopy = new User(original.name, original.age, original.email, original.address);

        // Verify the original and the shallow copy are not the same object
        assertNotSame(original, shallowCopy);

        // Verify the mutable Address field is not deeply copied
        assertSame(original.address, shallowCopy.address);

        // Verify the contents of the shallow copy are equal to the original
        assertEquals(original, shallowCopy);

        // Modify the mutable Address field in the shallow copy
        shallowCopy.address.city = "Los Angeles";

        // Verify that the original object is affected by changes in the shallow copy
        assertEquals(original.address.city, shallowCopy.address.city);
    }
}
