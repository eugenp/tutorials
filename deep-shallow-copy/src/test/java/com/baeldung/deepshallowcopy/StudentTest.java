package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void testClone() {
        Student st = new Student("1", 100);
        Student stCopy = (Student) st.clone();

        assertEquals(st.getAge(), stCopy.getAge());
        assertEquals(st.getName(), stCopy.getName());
    }

    @Test
    void testCopy() {
        Student st = new Student("1", 100);
        Student stCopy = new Student(st);

        assertEquals(st.getAge(), stCopy.getAge());
        assertEquals(st.getName(), stCopy.getName());
    }
}
