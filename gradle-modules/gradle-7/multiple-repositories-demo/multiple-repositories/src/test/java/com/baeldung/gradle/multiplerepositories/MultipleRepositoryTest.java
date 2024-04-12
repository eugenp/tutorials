package com.baeldung.gradle.multiplerepositories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleRepositoryTest {

    @Test
    public void testPublishedPackage() {
        Student student = new Student();
        student.setId(1);
        student.setStudentCode("CD-875");
        student.setName("John Doe");
        student.setLastInstitution("Institute of Technology");

        assertEquals("John Doe", student.getName());
    }
}
