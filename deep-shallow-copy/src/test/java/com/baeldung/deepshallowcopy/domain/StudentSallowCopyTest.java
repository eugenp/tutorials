package com.baeldung.deepshallowcopy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentSallowCopyTest {
    @Test
    void test() {
        Course course = new Course("a");
        StudentSallowCopy st = new StudentSallowCopy("name");
        st.addCourse(course);

        StudentSallowCopy stCopy = new StudentSallowCopy(st);
        course.setName("b");

        assertEquals("b", stCopy.getCourses().get(0).getName());
    }
}
