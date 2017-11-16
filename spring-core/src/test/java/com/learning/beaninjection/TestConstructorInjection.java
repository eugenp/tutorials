package com.learning.beaninjection;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.beaninjection.constructor.Person;

import static org.junit.Assert.*;

public class TestConstructorInjection {

    @Test
    public void testConstructorBeanInjection() {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextConstructorDI.xml");
        Person person = (Person)context.getBean("person");
        
        assertEquals("Mark", person.name);
        assertEquals("21", person.age.toString());
        assertEquals("H100", person.hobby.hobbyId);
        assertEquals("Singing", person.hobby.hobbyName);
        
    }
}
