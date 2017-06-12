package com.baeldung;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.model.Student;

public class SetterApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("setter-context.xml");

        Student student = (Student) context.getBean("student");
        student.introduceMyself();
    }
}
