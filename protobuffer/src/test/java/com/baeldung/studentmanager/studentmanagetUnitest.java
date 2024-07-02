package com.baeldung.studentmanager;

import baeldung.StudentOuterClass.Student;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class studentmanagerUnitest {

    @Test
    public void givenValidStudentDetails_whenCreateStudent_thenStudentIsCreatedSuccessfully() {
        Studentmanager studentmanager = new Studentmanager();
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 95);
        grades.put("Science", 90);

        Student student = studentManager.createStudent(1, "John Doe", grades);

        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals("John Doe", student.getName());
        assertEquals(95, student.getGradesMap().get("Math"));
        assertEquals(90, student.getGradesMap().get("Science"));
    }

    @Test
    public void givenSerializedStudent_whenDeserialize_thenStudentIsDeserializedSuccessfully() {
        Studentmanager studentmanager = new Studentmanager();
        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 95);
        grades.put("Science", 90);

        Student student = studentManager.createStudent(1, "John Doe", grades);
        byte[] byteArray = studentManager.serializeStudent(student);
        Student deserializedStudent = studentManager.deserializeStudent(byteArray);

        assertNotNull(deserializedStudent);
        assertEquals(1, deserializedStudent.getId());
        assertEquals("John Doe", deserializedStudent.getName());
        assertEquals(95, deserializedStudent.getGradesMap().get("Math"));
        assertEquals(90, deserializedStudent.getGradesMap().get("Science"));
    }
}