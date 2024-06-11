package com.baeldung.studentmanager;

import baeldung.StudentOuterClass.Student;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();

        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 95);
        grades.put("Science", 90);

        Student student = studentManager.createStudent(1, "John Doe", grades);
        byte[] byteArray = studentManager.serializeStudent(student);

        try {
            Student deserializedStudent = studentManager.deserializeStudent(byteArray);
            System.out.println("Student ID: " + deserializedStudent.getId());
            System.out.println("Student Name: " + deserializedStudent.getName());
            System.out.println("Grades: " + deserializedStudent.getGradesMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
