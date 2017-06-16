package com.baeldung.configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.model.Student;

public class SetterConfigTest {
    
    @Test
    public void whenContextLoaded_thenDependencyInjected() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SetterConfig.class);
        Student student = context.getBean(Student.class);
        assertEquals(2, student.getId());
        assertEquals("Tom", student.getName());
        assertEquals(2, student.getCollege().getId());
        assertEquals("Pittsburgh", student.getCollege().getName());
    }
}
