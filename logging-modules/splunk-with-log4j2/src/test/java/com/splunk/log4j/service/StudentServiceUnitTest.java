package com.splunk.log4j.service;

import com.splunk.log4j.dto.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudentServiceUnitTest {

    @Autowired
    private StudentService studentService;

    @Test
    void whenAddStudentCalled_thenReturnAddedStudent() {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNo(4);

        Student student2 = studentService.addStudent(student);

        Assertions.assertEquals(student2.getName(), student.getName());
        Assertions.assertEquals(student2.getRollNo(), student.getRollNo());
    }

    @Test
    void whenGetStudentsCalled_thdenReturnListOfStudent() {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNo(4);

        Student student2 = new Student();
        student.setName("Sham");
        student.setRollNo(5);

        studentService.addStudent(student);
        studentService.addStudent(student2);
        List<Student> studentList = studentService.getStudents();

        Student student3=studentList.stream().filter(s -> s.getRollNo()==5).findFirst().get();

        Assertions.assertNotNull(student3);
        Assertions.assertEquals(5,student3.getRollNo());
    }

    @Test
    void whenGetStudentCalled_thenStudentByIndex() {
        Student student = new Student();
        student.setName("Ram");
        student.setRollNo(4);

        studentService.addStudent(student);
        Student student2 = studentService.getStudent(4);

        Assertions.assertEquals(student2.getRollNo(), student.getRollNo());
        Assertions.assertEquals(student2.getName(), student.getName());
    }
}
