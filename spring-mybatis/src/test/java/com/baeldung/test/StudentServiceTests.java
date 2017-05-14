package com.baeldung.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.mybatis.model.Student;
import com.baeldung.spring.mybatis.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mybatis-spring.xml" })
public class StudentServiceTests {
    @Autowired
    StudentService studentService;

    @Test
    public void testInsertStudent() {
        Student student = new Student();
        student.setName("Santosh B S");
        student.setPassword("Test123");
        student.setUserName("santoshbs");

        boolean result = studentService.insertStudent(student);
        assertEquals("Studet details stored in database successfully", true, result);
    }

    @Test
    public void testRetrieveStudentByUserName() {
        Student student = studentService.getStudentByUserName("santoshbs");
        assertEquals("Retrieved student details are same as expected", "santoshbs", student.getUserName());
    }

    @Test
    public void testRetrieveStudentByLogin() {
        // Arrange
        Student student = studentService.getStudentByLogin("santoshbs", "Test123");
        // Act
        String studentName = student.getUserName();
        // Assert
        assertEquals("Retrieved student details are same as expected", "santoshbs", studentName);
    }
}
