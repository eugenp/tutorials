package com.baeldung.shallowcopyvsdeepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeepCopyUnitTest {

    @Test
    void whenCreatingDeepCopy_thenObjectsShouldNotBeSame() {
        Author author = new Author(1, "Baeldung");
        Course course = new Course(1, "Spring Masterclass", author);
        Student student = new Student(1, "John", course);
        Student deepCopy = new Student(student);

        Assertions.assertNotSame(student, deepCopy);
    }

    @Test
    void whenModifyingDeepCopy_thenOriginalObjectShouldNotChange() {
        Author author = new Author(1, "Baeldung");
        Course course = new Course(1, "Spring Masterclass", author);
        Student student = new Student(1, "John", course);
        Student deepCopy = new Student(student);

        deepCopy.getCourse()
            .setCourseName("Java Masterclass");

        Assertions.assertNotEquals(deepCopy.getCourse()
            .getCourseName(), student.getCourse()
            .getCourseName());
    }
}
