package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenChangingAnyFieldValuesAfterCopyConstructor_thenCopyFieldValuesShouldNotChange() {

        Address address = new Address("Fifth Avenue", "New York");
        Teacher classMaster = new Teacher("John", "Doe");
        Student original = new Student("First", "Student", classMaster, address);

        Student deepCopy = new Student(original);

        original.setFirstName("Second");
        address.setCity("Second");
        classMaster.setFirstName("Jane");

        assertNotEquals(original.getFirstName(), deepCopy.getFirstName());
        assertNotEquals(original.getAddress()
            .getCity(), deepCopy.getAddress()
                .getCity());
        assertNotEquals(original.getClassMaster()
            .getFirstName(), deepCopy.getClassMaster()
                .getFirstName());
    }

    @Test
    public void whenChangingAnyFieldValuesAfterCopyClone_thenCopyFieldValuesShouldNotChange() throws CloneNotSupportedException {

        Address address = new Address("Fifth Avenue", "New York");
        Teacher classMaster = new Teacher("John", "Doe");
        Student original = new Student("First", "Student", classMaster, address);

        Student deepCopy = (Student) original.clone();

        original.setFirstName("Second");
        address.setCity("Second");
        classMaster.setFirstName("Jane");

        assertNotEquals(original.getFirstName(), deepCopy.getFirstName());
        assertNotEquals(original.getAddress()
            .getCity(), deepCopy.getAddress()
                .getCity());
        assertNotEquals(original.getClassMaster()
            .getFirstName(), deepCopy.getClassMaster()
                .getFirstName());
    }
}
