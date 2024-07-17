package com.baeldung.deepcopyshallowcopy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeShallowCopyTest {

    @Test
    @DisplayName("Test of Shallow Copy - should copy only references due to which change in shallow copy object also changes original object")
    void shouldOnlyCopyReferences() {

        List<String> hobbies = new ArrayList<>();
        hobbies.add("Running");
        hobbies.add("swimming");

        Employee originalEmployee = new Employee("John", hobbies);

        // Shallow copy using copy constructor
        Employee shallowCopyEmployee = new Employee(originalEmployee);

        // Modifying the shallow copy
        originalEmployee.hobbies.add("playing");
        // Original object also changed along with shallow copy object
        assertEquals(originalEmployee.hobbies, shallowCopyEmployee.hobbies);
    }

    @Test
    @DisplayName("Test of Deep Copy - should make new copy due to which change in new  object doesn't changes original object")
    void shouldDoDeepCopy() {

        List<String> hobbies = new ArrayList<>();
        hobbies.add("Running");
        hobbies.add("swimming");
        Person originalPerson = new Person("John", hobbies);
        // deep copy using copy constructor
        Person shallowCopyPerson = new Person(originalPerson);

        // Modifying the deep copy
        shallowCopyPerson.hobbies.add("playing");
        // Original object remains unchanged
        assertNotEquals(originalPerson.hobbies, shallowCopyPerson.hobbies);


    }

}