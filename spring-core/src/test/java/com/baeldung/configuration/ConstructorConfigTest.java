package com.baeldung.configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.model.Student;

public class ConstructorConfigTest {

    @Test
    public void whenContextLoaded_thenDependencyInjected() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ConstructorConfig.class);
        Student student = context.getBean(Student.class);
        assertEquals(1, student.getId());
        assertEquals("John", student.getName());
        assertEquals(1, student.getCollege().getId());
        assertEquals("New York", student.getCollege().getName());
    }
}
