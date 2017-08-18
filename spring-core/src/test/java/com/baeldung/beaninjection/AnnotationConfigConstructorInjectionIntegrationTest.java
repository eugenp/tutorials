package com.baeldung.beaninjection;

import com.baeldung.configuration.ConstructorConfig;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.model.Student;

public class AnnotationConfigConstructorInjectionIntegrationTest {

    private static final int STUDENT_ID = 1;
    private static final String STUDENT_NAME = "Peter";
    private static final int SCHOOL_ID = 1;
    private static final String SCHOOL_NAME = "Massachusetts Institute of Technology";

    @Test
    public void givenJavaConfig_whenUsingConstructorInjection_thenCorrectStudentInfo() {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(ConstructorConfig.class);
        Student student = context.getBean(Student.class);
        assertEquals(STUDENT_ID, student.getId());
        assertEquals(STUDENT_NAME, student.getName());
        assertEquals(SCHOOL_ID, student.getSchool()
            .getId());
        assertEquals(SCHOOL_NAME, student.getSchool()
            .getName());
    }
}
