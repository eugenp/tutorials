package com.baeldung.spring.mybatis.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.mybatis.model.Student;
import com.baeldung.spring.mybatis.service.StudentService;

public class SpringMyBatisApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("mybatis-spring.xml");

        StudentService studentService = (StudentService) ctx.getBean("studentService");
        Student student = new Student();
        student.setName("Santosh B S");
        student.setPassword("Test123");
        student.setUserName("santoshbs");
        

        boolean result = studentService.insertStudent(student);
        if (result) {
            System.out.println("Student record saved successfully");
        } else {
            System.out.println("Encountered an error while saving student data");
        }

        final String userName = "santoshbs";
        Student matchingStudent = studentService.getStudentByUserName(userName);
        if (matchingStudent == null) {
            System.out.println("No matching student found for User Name - " + userName);
        } else {
            System.out.println("Student Details are as follows : ");
            System.out.println("Name : " + matchingStudent.getName());
            System.out.println("User Name : " + matchingStudent.getUserName());
        }

    }

}
