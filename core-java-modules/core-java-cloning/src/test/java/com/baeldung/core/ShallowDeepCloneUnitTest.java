package core;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import org.testng.annotations.Test;

import core.Person;

public class ShallowDeepCloneUnitTest {

    // Unit test for the shallowCopy() method
    @Test
    public void validateShallowCopy() {
        Person person1 = new Person("John", 28);
        // Create a shallow copy by assignment
        Person person2 = person1;
        // Updated the shallow copy
        person2.name = "Sarah";
        person2.age = 25;
        // person1 is also modified since it is a shallow copy
        assertEquals("Sarah", person1.name);
        assertEquals(25, person1.age);
        // Shallow copy references the same objects as the original
        assertSame(person1, person2);
    }

    @Test
    void validateDeepCopy() throws CloneNotSupportedException {
        Person person1 = new Person("John", 28);

        // Create a deep copy using clone()
        Person person2 = (Person) person1.clone();

        // Update the cloned person and age
        person2.name = "Sarah";
        person2.age = 25;

        // Original person remains unchanged
        assertEquals("John", person1.name);
        assertEquals(28, person1.age);

        // Cloned person and age are different
        assertEquals("Sarah", person2.name);
        assertEquals(25, person2.age);
    }
}