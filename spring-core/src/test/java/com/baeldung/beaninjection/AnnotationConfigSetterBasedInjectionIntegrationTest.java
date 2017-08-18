package com.baeldung.beaninjection;

import com.baeldung.configuration.SetterConfig;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.model.Student;

public class AnnotationConfigSetterBasedInjectionIntegrationTest {

    private static final int STUDENT_ID = 2;
    private static final String STUDENT_NAME = "Sam";
    private static final int SCHOOL_ID = 2;
    private static final String SCHOOL_NAME = "BUET";

    @Test
    public void givenJavaConfig_whenUsingSetterBasedInjection_thenCorrectStudentInfo() {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(SetterConfig.class);
        Student student = context.getBean(Student.class);
        assertEquals(STUDENT_ID, student.getId());
        assertEquals(STUDENT_NAME, student.getName());
        assertEquals(SCHOOL_ID, student.getSchool()
            .getId());
        assertEquals(SCHOOL_NAME, student.getSchool()
            .getName());
    }

}
