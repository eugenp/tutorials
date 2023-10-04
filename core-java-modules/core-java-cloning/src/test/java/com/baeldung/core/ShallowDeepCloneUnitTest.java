package core;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.Test;

import core.Person;
import core.Address;

public class ShallowDeepCloneUnitTest {

    // Unit test for the shallowCopy() method
    @Test
    public void validateShallowCopy() {
        Address address = new Address("Paris");
        Person originalPerson = new Person("Taylor", address);
        // Create a shallow copy by assignment
        Person copiedPerson = originalPerson.shallowCopy();
        copiedPerson.setName("Selena");
        copiedPerson.getAddress().setCity("LA");

        // Verify the primitive values of the shallowCopy object
        assertNotEquals(originalPerson.getName(), copiedPerson.getName());
        // Check if the original person's address is also modified (shallow copy)
        assertEquals(originalPerson.getAddress(), copiedPerson.getAddress());
    }

    @Test
    void validateDeepCopy() throws CloneNotSupportedException {
        Address address = new Address("Paris");
        Person originalPerson = new Person("Taylor", address);

        Person clonedPerson = (Person) originalPerson.clone();

        // Verify the primitive values of the deepCopy object
        assertEquals(originalPerson.getName(), clonedPerson.getName());
        assertEquals(originalPerson.getAddress().getCity(), clonedPerson.getAddress().getCity());
        // Check if the original person's address is also modified (deep copy)
        assertNotEquals(originalPerson.getAddress(), clonedPerson.getAddress());
    }
}
