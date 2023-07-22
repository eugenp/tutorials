package com.baeldung.copy;

import com.baeldung.java.copy.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowCopyUnitTest {
    @Test
    void shallowCopy() {
        Student student1 = new Student("James", 24);
        Student student2 = student1;
        student2.setName("Jackson");

        assertEquals(student1.getName(), student2.getName());
    }

    @Test
    void deepCopy() {
        Student student1 = new Student("James", 24);
        Student student2 = new Student(new String(student1.getName()), student1.getAge());
        student2.setAge(28);
        student2.setName("Jackson");

        assertNotEquals(student1.getName(), student2.getName());
        assertNotEquals(student1.getAge(), student2.getAge());
    }



}