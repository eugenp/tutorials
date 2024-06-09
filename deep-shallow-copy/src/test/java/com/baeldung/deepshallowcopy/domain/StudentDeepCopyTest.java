package com.baeldung.deepshallowcopy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDeepCopyTest {
    @Test
    void test() {
        Course course = new Course("a");
        StudentDeepCopy st = new StudentDeepCopy("name");
        st.addCourse(course);

        StudentDeepCopy stCopy = new StudentDeepCopy(st);
        course.setName("b");

        assertNotEquals("b", stCopy.getCourses().get(0).getName());
    }
}
