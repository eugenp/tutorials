package com.learning.beaninjection;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.beaninjection.setter.Person;

import static org.junit.Assert.assertEquals;

public class TestSetterInjection {

    private ApplicationContext context;

    @Test
    public void testSetterBeanInjection() {

        context = new ClassPathXmlApplicationContext("applicationContextSetterDI.xml");

        Person person = (Person)context.getBean("person");

        assertEquals("Phillip", person.getName());
        assertEquals("30", person.getAge().toString());
        assertEquals("H200", person.getHobby().getHobbyId());
        assertEquals("Painting", person.getHobby().getHobbyName());
        
    }
}
