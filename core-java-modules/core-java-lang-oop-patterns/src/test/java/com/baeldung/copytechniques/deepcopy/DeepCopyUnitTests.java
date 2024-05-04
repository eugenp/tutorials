package com.baeldung.copytechniques.deepcopy;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class DeepCopyUnitTests {
    @Test
    void whenDeepCopying_thenObjectsShouldNotBeSame() throws CloneNotSupportedException {
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
        Student deepCopy = (Student) original.clone();

        deepCopy.setName("Jane");

        assertNotEquals(original.getName(), deepCopy.getName());
    }

    @Test
    void whenModifyingSharedReferenceInCopy_thenOriginalShouldChange() throws CloneNotSupportedException {
        Department department = new Department("Science");
        Student original = new Student("John", department);
        Student deepCopy = (Student) original.clone();

        original.getDepartment()
                .setName("Math");

        assertNotEquals(original.getDepartment()
                .getName(), deepCopy.getDepartment()
                .getName());
    }
}
