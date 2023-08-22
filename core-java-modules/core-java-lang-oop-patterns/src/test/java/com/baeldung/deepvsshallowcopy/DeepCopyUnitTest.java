package com.baeldung.deepvsshallowcopy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {
    @Test
    public void  whenCreatingDeepCopyWithCopyConstructor_thenObjectsShouldNotBeSame() {
        Course course = new Course("Algorithms and Data Structures", 40, "Computer Science", "Campus A");
        Professor prof = new Professor("John", " Doe", course);

        Professor deepProf = new Professor(prof);

        assertNotSame(deepProf, prof);
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {

        Course course = new Course("Algorithms and Data Structures", 40, "Computer Science", "Campus A");
        Professor prof = new Professor("John", " Doe", course);

        Professor deepProf = new Professor(prof);
        course.setCampus("Campus C");

        assertNotEquals(deepProf.getCourse().getCampus(), prof.getCourse().getCampus());
    }
}
