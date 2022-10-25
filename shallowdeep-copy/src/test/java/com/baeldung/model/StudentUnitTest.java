package com.baeldung.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentUnitTest {

    @Test
    public void shouldMakeAShallowCLoneOfStudent() throws CloneNotSupportedException {

        Student anne = new Student();
        anne.setAge(18);
        anne.addSubject("English");
        anne.addSubject("Maths");

        Student sally = anne.clone();
        // anne now decides to take Physics
        anne.setAge(20);
        anne.addSubject("Physics");

        assertAll(() -> assertEquals(18, sally.getAge()),
                () -> assertTrue(sally.getSubjects().stream().anyMatch("Physics"::equals))
        );

    }

    @Test
    public void shouldMakeADeepCloneOfStudent() throws CloneNotSupportedException {

        Student anne = new DeepStudent();

        anne.setAge(18);
        anne.addSubject("English");
        anne.addSubject("Maths");

        Student sally = anne.clone();

        anne.setAge(20);
        anne.addSubject("Physics");

        assertAll(() -> assertEquals(18, sally.getAge()),
                () -> assertEquals(3, anne.getSubjects().size()),
                () -> assertEquals(2, sally.getSubjects().size()),
                () -> assertTrue(sally.getSubjects().stream().noneMatch("Physics"::equals))
        );
    }
}