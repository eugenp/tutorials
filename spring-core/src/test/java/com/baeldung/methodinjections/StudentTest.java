package com.baeldung.methodinjections;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentTest {

    @Test
    public void whenPrototypeInjectedToSingleton_thenNewPrototypeInstanceEverytime() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student1 = context.getBean("studentBean", Student.class);
        Student student2 = context.getBean("studentBean", Student.class);

        Assert.assertEquals(student1, student2);
        Assert.assertNotEquals(student1.getNotification("Message1"), student2.getNotification("Message2"));
        context.close();
    }

    @Test
    public void whenAbstractGetterMethodInjects_thenNewPrototypeBean() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentService service = context.getBean("studentService", StudentService.class);
        SchoolNotification notification = service.getSchoolNotification("Test");
        
        Assert.assertNotNull(notification);
        Assert.assertEquals("Test", notification.getMessage());
        context.close();
    }
}
