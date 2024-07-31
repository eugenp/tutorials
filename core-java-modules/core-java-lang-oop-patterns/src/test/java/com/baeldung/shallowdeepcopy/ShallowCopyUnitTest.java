package com.baeldung.shallowdeepcopy;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopy_thenInstancesShouldBeDifferent() {

        Address address = new Address("Fifth Avenue", "New York");
        Teacher classMaster = new Teacher("John", "Doe");
        Student original = new Student("First", "Student", classMaster, address);

        Student shallowCopy = new Student(original.getFirstName(), original.getLastName(), original.getClassMaster(), original.getAddress());

        assertTrue(shallowCopy != original);
    }

    @Test
    public void whenChangingImmutableFieldValues_thenCopyFieldValuesShouldNotChange() {

        Address address = new Address("Fifth Avenue", "New York");
        Teacher classMaster = new Teacher("John", "Doe");
        Student original = new Student("First", "Student", classMaster, address);

        Student shallowCopy = new Student(original.getFirstName(), original.getLastName(), original.getClassMaster(), original.getAddress());

        original.setFirstName("Second");

        assertNotEquals(original.getFirstName(), shallowCopy.getFirstName());
    }

    @Test
    public void whenChangingOriginalFieldValues_thenCopyFieldValuesChange() {

        Address address = new Address("Fifth Avenue", "New York");
        Teacher classMaster = new Teacher("John", "Doe");
        Student original = new Student("First", "Student", classMaster, address);

        Student shallowCopy = new Student(original.getFirstName(), original.getLastName(), original.getClassMaster(), original.getAddress());

        classMaster.setFirstName("Jane");
        address.setCity("London");

        assertEquals(original.getClassMaster()
            .getFirstName(), shallowCopy.getClassMaster()
                .getFirstName());
        assertEquals(original.getAddress()
            .getCity(), shallowCopy.getAddress()
                .getCity());
    }
}
