package com.baeldung.injectiontypes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionTest {

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {

        ApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
        StudentServiceWithSetterInjection studentService = context.getBean(StudentServiceWithSetterInjection.class);

        Student student = new Student(1000, "Baeldung");

        assertTrue(studentService.save(student).equalsIgnoreCase("Baeldung"));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {

        ApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
        StudentServiceWithConstructorInjection studentService = context
            .getBean(StudentServiceWithConstructorInjection.class);

        Student student = new Student(1000, "Baeldung");

        assertTrue(studentService.save(student).equalsIgnoreCase("Baeldung"));
    }

}
