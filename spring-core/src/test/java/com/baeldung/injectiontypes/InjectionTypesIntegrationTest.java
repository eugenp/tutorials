package com.baeldung.injectiontypes;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionTypesIntegrationTest {
    
    @Test
    public void testBasicUsage() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-injection-types.xml");
        Course course = context.getBean("course",Course.class);
        System.out.println(course.getPriority());
        System.out.println(course.getTeacher().getName());
    }
    
}
