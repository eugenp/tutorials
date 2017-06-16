package com.baeldung;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.configuration.ConstructorConfig;
import com.baeldung.model.Student;

public class ConstructorApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConstructorConfig.class);
        Student student = context.getBean(Student.class);
        student.introduceMyself();
    }
}
