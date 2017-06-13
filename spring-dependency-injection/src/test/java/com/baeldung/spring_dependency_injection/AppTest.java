package com.baeldung.spring_dependency_injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        ApplicationContext contextJava = new AnnotationConfigApplicationContext(Config.class);

        Person personJavaConstructor = (Person) contextJava.getBean("personJavaConstructor");
        assertEquals("James", personJavaConstructor.getName());
        assertEquals(24, personJavaConstructor.getAge());

        Person personJavaSetter = (Person) contextJava.getBean("personJavaSetter");
        assertEquals("John", personJavaSetter.getName());
        assertEquals(25, personJavaSetter.getAge());

        ApplicationContext contextXml = new ClassPathXmlApplicationContext("spring-config.xml");

        Person personXmlConstructor = (Person) contextXml.getBean("personXmlConstructor");
        assertEquals("Robert", personXmlConstructor.getName());
        assertEquals(26, personXmlConstructor.getAge());

        Person personXmlSetter = (Person) contextXml.getBean("personXmlSetter");
        assertEquals("Michael", personXmlSetter.getName());
        assertEquals(27, personXmlSetter.getAge());
    }
}
