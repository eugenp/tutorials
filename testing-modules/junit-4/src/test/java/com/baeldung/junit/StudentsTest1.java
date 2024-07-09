package com.baeldung.junit;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StudentsTest1 {

    @Test
    public void testDeepCopy() throws CloneNotSupportedException {
        Courses1 course = new Courses1("Java");
        Students1 originalStudent = new Students1("Anees", course);
        Students1 deepCopy = (Students1) originalStudent.clone();

        assertEquals("Java", originalStudent.course.courseName);
        assertEquals("Java", deepCopy.course.courseName);

        originalStudent.course.courseName = "Python";

        assertEquals("Python", originalStudent.course.courseName);
        assertEquals("Java", deepCopy.course.courseName);
    }
}
