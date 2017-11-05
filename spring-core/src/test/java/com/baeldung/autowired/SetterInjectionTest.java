package com.baeldung.autowired;

import com.baeldung.setterdi.Address;
import com.baeldung.setterdi.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class SetterInjectionTest {


    @Mock
    Address address;
    @InjectMocks
    Person person;
    ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("setterdi.xml");
    }

    @Test
    public void testAnnotationSetterInjection() {
        assertNotNull(person);
        assertNotNull(person.getAddress());
    }

    @Test
    public void testXmlSetterInjection() {
        Person person = applicationContext.getBean("person", Person.class);
        assertNotNull(person);
        assertNotNull(person.getAddress());
    }

}
