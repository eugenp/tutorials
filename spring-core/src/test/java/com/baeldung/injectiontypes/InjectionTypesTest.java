package com.baeldung.injectiontypes;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class InjectionTypesTest {
    
    @Test
    public void givenAppConfig_whenGetBean_thenValuesValid() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Appconfig.class);
        Course course = context.getBean(Course.class);
        Assert.assertEquals(5, course.getPriority());
        Assert.assertEquals("Mr. Baeldung", course.getTeacher().getName());
    }
    
}
