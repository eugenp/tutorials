package com.splunk.log4j.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.splunk.log4j.dto.Student;

@SpringBootTest
class StudentServiceUnitTest {

    @Autowired
    private StudentService studentService;

    @Test
    void whenAddStudentCalled_thenReturnAddedStudent() {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNumber(4);

        Student student2 = studentService.addStudent(student);

        Assertions.assertEquals(student2.getName(), student.getName());
        Assertions.assertEquals(student2.getRollNumber(), student.getRollNumber());
    }

    @Test
    void whenGetStudentsCalled_thenReturnListOfStudent() {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNumber(4);

        Student student2 = new Student();
        student.setName("Sham");
        student.setRollNumber(5);

        studentService.addStudent(student);
        studentService.addStudent(student2);
        List<Student> studentList = studentService.getStudents();

        Student student3 = studentList.stream()
          .filter(s -> s.getRollNumber() == 5)
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Student not found"));

        Assertions.assertNotNull(student3);
        Assertions.assertEquals(5, student3.getRollNumber());
    }

    @Test
    void whenGetStudentCalled_thenStudentByIndex() {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNumber(4);

        studentService.addStudent(student);
        Student student2 = studentService.getStudent(4);

        Assertions.assertEquals(student2.getRollNumber(), student.getRollNumber());
        Assertions.assertEquals(student2.getName(), student.getName());
    }
}
