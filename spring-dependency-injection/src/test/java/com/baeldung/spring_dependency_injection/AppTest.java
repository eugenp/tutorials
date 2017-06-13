package com.baeldung.spring_dependency_injection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {

    @Test
    public void givenJavaConfiguration_WhenOnConstructor_ThenValidDependency() {
        ApplicationContext contextJava = new AnnotationConfigApplicationContext(Config.class);

        Person personJavaConstructor = (Person) contextJava.getBean("personJavaConstructor");
        assertEquals("James", personJavaConstructor.getName());
        assertEquals(24, personJavaConstructor.getAge());
    }

    @Test
    public void givenJavaConfiguration_WhenOnSetter_ThenValidDependency() {
        ApplicationContext contextJava = new AnnotationConfigApplicationContext(Config.class);

        Person personJavaSetter = (Person) contextJava.getBean("personJavaSetter");
        assertEquals("John", personJavaSetter.getName());
        assertEquals(25, personJavaSetter.getAge());
    }

    @Test
    public void givenXmlConfiguration_WhenOnConstructor_ThenValidDependency() {
        ApplicationContext contextXml = new ClassPathXmlApplicationContext("spring-config.xml");

        Person personXmlConstructor = (Person) contextXml.getBean("personXmlConstructor");
        assertEquals("Robert", personXmlConstructor.getName());
        assertEquals(26, personXmlConstructor.getAge());
    }

    @Test
    public void givenXmlConfiguration_WhenOnSetter_ThenValidDependency() {
        ApplicationContext contextXml = new ClassPathXmlApplicationContext("spring-config.xml");

        Person personXmlSetter = (Person) contextXml.getBean("personXmlSetter");
        assertEquals("Michael", personXmlSetter.getName());
        assertEquals(27, personXmlSetter.getAge());
    }

}
