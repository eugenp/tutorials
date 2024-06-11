package com.baeldung.studentmanager;

import baeldung.StudentOuterClass.Student;
import java.util.Map;

public class StudentManager {

    public Student createStudent(int id, String name, Map<String, Integer> grades) {
        Student.Builder studentBuilder = Student.newBuilder();
        studentBuilder.setId(id);
        studentBuilder.setName(name);
        studentBuilder.putAllGrades(grades);
        return studentBuilder.build();
    }

    public byte[] serializeStudent(Student student) {
        return student.toByteArray();
    }

    public Student deserializeStudent(byte[] byteArray) {
        try {
            return Student.parseFrom(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
