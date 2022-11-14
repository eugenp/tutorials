package com.baeldung.objectcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeepAndShallowCopyTest {

    @Test
    public void givenShallowCopying_whenModifyingOriginalObject_thenCopyShouldChange() {
        Course course = new Course("C201", "Basics of Programming");
        Student student = new Student(1, "Abby", course);
        Student shallowCopy = new Student(student.getRollNo(), student.getName(), student.getCourse());

        student.setName("John");
        course.setName("Basics of Java Programming");
        student.setCourse(course);

        assertNotSame(student, shallowCopy);
        assertNotEquals(student.getName(), shallowCopy.getName());
        assertEquals(student.getCourse().getName(), shallowCopy.getCourse().getName());
    }

    @Test
    public void givenDeepCopying_whenModifyingOriginalObject_thenCopyShouldNotChange() {
        Course course = new Course("C201", "Basics of Programming");
        Student student = new Student(1, "Abby", course);
        Student deepCopy = new Student(student);

        student.setName("John");
        course.setName("Basics of Java Programming");
        student.setCourse(course);

        assertNotEquals(student.getName(), deepCopy.getName());
        assertNotEquals(student.getCourse().getName(), deepCopy.getCourse().getName());
    }

    @Test
    public void whenOverridingCloneable_thenMutableFieldsShouldNotBeSame() {
        Course course = new Course("C201", "Discreet Mathematics");
        Student student = new Student(1, "John", course);
        Student studentClone = student.clone();

        course.setName("Object Oriented Programming");
        student.setCourse(course);

        assertNotSame(student, studentClone);
        assertNotEquals(student.getCourse().getName(), studentClone.getCourse().getName());
    }
}


