package com.baeldung.java.hexagonalarchitecture;

import com.baeldung.java.hexagonalarchitecture.adapter.StudentServiceImpl;
import com.baeldung.java.hexagonalarchitecture.domain.Student;
import com.baeldung.java.hexagonalarchitecture.port.StudentService;

public class App {
    public static void main(String[] args) {
        Student student = new Student();
        StudentService studentService = new StudentServiceImpl();
        student.setName("John");
        student.setSurname("Doe");
        student.setCourse("Computer Science");
        student.setGrade(10);
        studentService.createStudentService(student);
        System.out.println(studentService.listStudentService());
    }
}
