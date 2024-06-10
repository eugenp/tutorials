package com.example;

import example.StudentOuterClass.Student;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentManagerTest {

    @Test
    public void testCreateStudent() {
        StudentManager studentManager = new StudentManager();
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
    public void testSerializeAndDeserializeStudent() {
        StudentManager studentManager = new StudentManager();
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
