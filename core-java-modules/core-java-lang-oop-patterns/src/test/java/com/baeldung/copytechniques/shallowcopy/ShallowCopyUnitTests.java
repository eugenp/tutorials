package com.baeldung.copytechniques.shallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShallowCopyUnitTests {

    @Test
    void whenShallowCopying_thenObjectsShouldNotBeSame() throws CloneNotSupportedException {
        Department department = new Department("Science");
        Student original = new Student("John", department);
        Student copy = (Student) original.clone();

        assertNotSame(original, copy);
    }

    @Test
    void whenModifyingOriginalObject_thenCopyShouldNotChange() throws CloneNotSupportedException {
        Department department = new Department("Science");
        Student original = new Student("John", department);
        Student shallowCopy = (Student) original.clone();

        original.setName("Jane");

        assertNotEquals(original, shallowCopy);
    }

    @Test
    void whenModifyingCopyObject_thenOriginalShouldNotChange() throws CloneNotSupportedException {
        Department department = new Department("Science");
        Student original = new Student("John", department);
        Student shallowCopy = (Student) original.clone();

        shallowCopy.setName("Jane");

        assertNotEquals(original, shallowCopy);
    }


    @Test
    void whenModifyingSharedReferenceInCopy_thenOriginalShouldNotChange() throws CloneNotSupportedException {
        Department department = new Department("Science");
        Student original = new Student("John", department);
        Student shallowCopy = (Student) original.clone();

        original.getDepartment()
                .setName("Math");

        assertEquals(original.getDepartment()
                .getName(), shallowCopy.getDepartment()
                .getName());
    }


}
