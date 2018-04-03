package com.baeldung.methodinjections;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {

    @Test
    public void whenLookupMethodCalled_thenNewInstanceReturned() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student1 = context.getBean("studentBean", Student.class);
        Student student2 = context.getBean("studentBean", Student.class);

        Assert.assertEquals(student1, student2);
        Assert.assertNotEquals(student1.getNotification("Sam", 76), student2.getNotification("Paul", 44));
        context.close();
    }

    @Test
    public void whenAbstractGetterMethodInjects_thenNewInstanceReturned() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentService service = context.getBean("studentService", StudentService.class);
        String name = "Alex";
        int marks = 96;
        String expectedResult = name + ":FIRST_CLASS";

        String actualResult = service.checkResult(name, marks);
        Assert.assertEquals(expectedResult, actualResult);
        context.close();
    }
}
