package com.example;

import example.StudentOuterClass.Student;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Student.Builder studentBuilder = Student.newBuilder();
        studentBuilder.setId(1);
        studentBuilder.setName("John Doe");


        Map<String, Integer> grades = new HashMap<>();
        grades.put("Math", 95);
        grades.put("Science", 90);


        studentBuilder.putAllGrades(grades);


        Student student = studentBuilder.build();


        byte[] byteArray = student.toByteArray();


        try {
            Student deserializedStudent = Student.parseFrom(byteArray);
            System.out.println("Student ID: " + deserializedStudent.getId());
            System.out.println("Student Name: " + deserializedStudent.getName());
            System.out.println("Grades: " + deserializedStudent.getGradesMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
