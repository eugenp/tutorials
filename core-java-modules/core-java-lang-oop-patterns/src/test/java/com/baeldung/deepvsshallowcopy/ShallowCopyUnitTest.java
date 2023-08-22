package com.baeldung.deepvsshallowcopy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {
    @Test
    public void whenShallowCopying_thenObjectsShouldBeDifferent() {
        Course course = new Course("Algorithms and Data Structures", 40, "Computer Science", "Campus A");
        Professor prof = new Professor("John", " Doe", course);

        Professor shallowProf = new Professor(
                prof.getFirstName(), prof.getLastName(), prof.getCourse());

        assertNotSame(shallowProf, prof, "Shallow copy not the same as original");
    }

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() {
        Course course = new Course("Algorithms and Data Structures", 40, "Computer Science", "Campus A");
        Professor prof = new Professor("John", " Doe", course);

        Professor shallowProf = new Professor(
                prof.getFirstName(), prof.getLastName(), prof.getCourse());
        course.setCampus("Campus C");

        assertEquals(shallowProf.getCourse().getCampus(), course.getCampus());
    }
}
