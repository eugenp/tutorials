package com.baeuldung.java.copy;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Student class, specifically testing the shallow and deep copy functionalities.
 */
public class StudentTest {

    /**
     * Tests the shallow copy functionality of the Student class.
     * Ensures that the main student object is a different reference, but the nested Course object is the same reference.
     * @throws CloneNotSupportedException if the object's class does not implement the Cloneable interface.
     */
    @Test
    void testShallowCopy() throws CloneNotSupportedException {
        Student student1 = new Student("John", "Math");
        Student student2 = (Student) student1.clone();
        assertNotSame(student1, student2);
        assertSame(student1.course, student2.course);  // References are same
    }

    /**
     * Tests the deep copy functionality of the Student class.
     * Ensures that both the main student object and the nested Course object are different references.
     * @throws CloneNotSupportedException if the object's class does not implement the Cloneable interface.
     */
    @Test
    void testDeepCopy() throws CloneNotSupportedException {
        Student student1 = new Student("John", "Math");
        Student student3 = (Student) student1.deepClone();
        assertNotSame(student1, student3);
        assertNotSame(student1.course, student3.course);  // References are different
    }
}
