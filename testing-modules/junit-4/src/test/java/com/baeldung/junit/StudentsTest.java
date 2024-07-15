package com.baeldung.junit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StudentsTest {
@Test
public void testShallowCopy() throws CloneNotSupportedException {
    Courses course = new Courses("Java");
    Students originalStudent = new Students("Anees", course);
    Students shallowCopy = (Students) originalStudent.clone();
    assertEquals("Java", originalStudent.course.courseName);
    assertEquals("Java", shallowCopy.course.courseName);
    originalStudent.course.courseName = "Python";
    assertEquals("Python", originalStudent.course.courseName);
    assertEquals("Python", shallowCopy.course.courseName);
    }
}
