package com.baeldung.applicationcontext;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class ClasspathXmlApplicationContextIntegrationTest {
    @Test
    public void testBasicUsage() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpathxmlapplicationcontext-example.xml");
        Student student = ((Student) context.getBean("student"));
        assertThat(student.getNo()).isEqualTo(15);
        assertThat(student.getName()).isEqualTo("Tom");

        Student sameStudent = context.getBean("student", Student.class);// do not need cast class
        assertThat(sameStudent.getNo()).isEqualTo(15);
        assertThat(sameStudent.getName()).isEqualTo("Tom");
    }

    @Test
    public void testRegisterShutdownHook() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpathxmlapplicationcontext-example.xml");
        context.registerShutdownHook();
    }

    @Test
    public void testInternationalization() {
        MessageSource resources = new ClassPathXmlApplicationContext("classpathxmlapplicationcontext-internationalization.xml");

        String enHello = resources.getMessage("hello", null, "Default", Locale.ENGLISH);
        String enYou = resources.getMessage("you", null, Locale.ENGLISH);
        String enThanks = resources.getMessage("thanks", new Object[]{enYou}, Locale.ENGLISH);
        assertThat(enHello).isEqualTo("hello");
        assertThat(enThanks).isEqualTo("thank you");

        String chHello = resources.getMessage("hello", null, "Default", Locale.SIMPLIFIED_CHINESE);
        String chYou = resources.getMessage("you", null, Locale.SIMPLIFIED_CHINESE);
        String chThanks = resources.getMessage("thanks", new Object[]{chYou}, Locale.SIMPLIFIED_CHINESE);
        assertThat(chHello).isEqualTo("你好");
        assertThat(chThanks).isEqualTo("谢谢你");
    }

    @Test
    public void testApplicationContextAware() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpathxmlapplicationcontext-example.xml");
        Teacher teacher = context.getBean("teacher", Teacher.class);
        List<Course> courses = teacher.getCourses();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getName()).isEqualTo("math");
    }
}
