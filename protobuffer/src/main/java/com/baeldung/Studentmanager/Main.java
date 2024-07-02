package com.baeldung.studentmanager;

import baeldung.StudentOuterClass.Student;
import baeldung.StudentOuterClass.Course;
import baeldung.StudentOuterClass.MultiTypeMap;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        StudentManager studentmanager = new Studentmanager();

        Map<String, Course> courses = new HashMap<>();
        Course mathCourse = Course.newBuilder().setName("Math").putGrades("John Doe", 95).build();
        Course scienceCourse = Course.newBuilder().setName("Science").putGrades("John Doe", 90).build();
        courses.put("Math", mathCourse);
        courses.put("Science", scienceCourse);
        Student studentWithCourses = studentManager.createStudentWithCourses(1, "John Doe", courses);
        byte[] byteArray = studentManager.serializeStudent(studentWithCourses);

        try {
            Student deserializedStudent = studentmanager.deserializeStudent(byteArray);
            System.out.println("Student ID: " + deserializedStudent.getId());
            System.out.println("Student Name: " + deserializedStudent.getName());
            System.out.println("Courses: " + deserializedStudent.getCoursesMap());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, String> intToStringMap = new HashMap<>();
        intToStringMap.put(1, "One");
        intToStringMap.put(2, "Two");

        Map<String, Double> stringToDoubleMap = new HashMap<>();
        stringToDoubleMap.put("pi", 3.14);
        stringToDoubleMap.put("e", 2.718);

        Map<Boolean, byte[]> boolToBytesMap = new HashMap<>();
        boolToBytesMap.put(true, new byte[]{0x01, 0x02});
        boolToBytesMap.put(false, new byte[]{0x03, 0x04});

        MultiTypeMap multiTypeMap = studentManager.createMultiTypeMap(intToStringMap, stringToDoubleMap, boolToBytesMap);
        System.out.println("MultiTypeMap: " + multiTypeMap);
    }
}