package com.baeldung.copy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowVsDeepCopyUnitTest {

    @Test
    void whenShallowCopyVersionIsModified_thenOriginalVersionIsAlsoChanged() throws CloneNotSupportedException {
        Lesson originalLesson = new Lesson("CSE-01", 10);
        ShallowCopyStudent originalStudent = new ShallowCopyStudent("Doga", 1, originalLesson);

        ShallowCopyStudent copiedStudent = (ShallowCopyStudent) originalStudent.clone();
        copiedStudent.setName("Zeynep");
        copiedStudent.setGrade(3);
        copiedStudent.getLesson()
            .setName("CSE-07");
        copiedStudent.getLesson()
            .setCredit(5);

        assertEquals("CSE-07", originalStudent.getLesson()
            .getName());
        assertEquals(5, originalStudent.getLesson()
            .getCredit());
        assertEquals(copiedStudent.getLesson()
            .getName(), originalStudent.getLesson()
            .getName());
        assertEquals(copiedStudent.getLesson()
            .getCredit(), originalStudent.getLesson()
            .getCredit());
    }

    @Test
    void whenDeepCopyVersionIsModified_thenOriginalVersionIsNotChanged() throws CloneNotSupportedException {
        Lesson originalLesson = new Lesson("CSE-01", 10);
        DeepCopyStudent originalStudent = new DeepCopyStudent("Doga", 1, originalLesson);

        DeepCopyStudent copiedStudent = (DeepCopyStudent) originalStudent.clone();
        copiedStudent.setName("Zeynep");
        copiedStudent.setGrade(3);
        copiedStudent.getLesson()
            .setName("CSE-07");
        copiedStudent.getLesson()
            .setCredit(5);

        assertEquals("CSE-01", originalStudent.getLesson()
            .getName());
        assertEquals(10, originalStudent.getLesson()
            .getCredit());
        assertNotEquals(copiedStudent.getLesson()
            .getName(), originalStudent.getLesson()
            .getName());
        assertNotEquals(copiedStudent.getLesson()
            .getCredit(), originalStudent.getLesson()
            .getCredit());
    }
}
