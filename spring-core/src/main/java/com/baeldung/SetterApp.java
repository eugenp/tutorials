package com.baeldung;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.baeldung.configuration.SetterConfig;
import com.baeldung.model.Student;

public class SetterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SetterConfig.class);
        Student student = context.getBean(Student.class);
        student.introduceMyself();
    }
}
