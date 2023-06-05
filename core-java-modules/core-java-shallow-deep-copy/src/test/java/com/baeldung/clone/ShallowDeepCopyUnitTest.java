package core;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotSame;

import org.testng.annotations.Test;

import core.Address;
import core.Person;

public class ShallowDeepCopyUnitTest {

    // Unit test for the shallowCopy() method
    @Test
    public void givenTwoDifferentObjects_whenCloned_checkShallowCopy() {
        Address address = new Address("Bengaluru");
        Person original = new Person("Rajat", 29, address);
        // Shallow copy
        Person shallowCopy = original.shallowCopy();
        shallowCopy.setName("Gurwinder");
        shallowCopy.setAge(33);
        shallowCopy.getAddress().setCity("Chandigarh");

        // Verify the primitive values of the shallowCopy object
        assertNotEquals(original.getName(), shallowCopy.getName());
        assertNotEquals(original.getAge(), shallowCopy.getAge());
        // Verify that the address reference is the same in both objects
        assertEquals(original.getAddress(), shallowCopy.getAddress());
    }

    // Unit test for the deepCopy() method
    @Test
    void givenTwoDifferentObjects_whenCloned_checkDeepCopy() {
        Address address = new Address("Shimla");
        Person original = new Person("Narender", 38, address);
        // Deep Copy
        Person deepCopy = original.deepCopy();
        // Verify the field values of the deepCopy object
        assertEquals(original.getName(), deepCopy.getName());
        assertEquals(original.getAge(), deepCopy.getAge());

        // Verify that the address reference is different in both objects
        assertNotSame(original.getAddress(), deepCopy.getAddress());
        // Verify that the address city is the same in both objects
        assertEquals(original.getAddress().getCity(), deepCopy.getAddress().getCity());
    }
}
