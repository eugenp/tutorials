package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    public void testThatShallowCopyDoesntCopyMemberObjects() {
        List<Grade> grades = List.of(new Grade("Java", "3.5"));
        Student s1 = new Student("Ash", "Smith", grades);
        Student s2 = CopyUtils.shallowCopy(s1);


        assertNotSame(s1, s2);
        assertSame(s1.getGrades(), s2.getGrades());
    }

    @Test
    public void testThatDeepCopyMakesCopyOfMemberObjects() {
        List<Grade> grades = List.of(new Grade("Java", "3.5"));
        Student s1 = new Student("Ash" ,"Smith", grades);
        Student s2 = CopyUtils.deepCopy(s1);

        assertNotSame(s1, s2);
        assertNotSame(s1.getGrades(), s2.getGrades());
        assertNotSame(s1.getGrades().get(0), s2.getGrades().get(0));
    }

}
