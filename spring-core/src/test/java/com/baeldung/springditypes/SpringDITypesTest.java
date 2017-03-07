package com.baeldung.springditypes;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by gerald on 3/6/17.
 */
public class SpringDITypesTest {
    private ApplicationContext context;

    @Before
    public void setUpApplicationContext(){
        context = new ClassPathXmlApplicationContext("classpathxmlapplicationcontext-injectiontypeexample.xml");
    }

    @Test
    public void givenXmlConfiguration_WhenConstructorValueIsInjected_thenValuesAreSet(){
        Driver driver = context.getBean("driver", Driver.class);

        assertNotNull(driver);
        assertEquals(driver.getName(), "taxiDriver");
        TestCase.assertEquals(driver.getAge(), 26);
    }

    @Test
    public void givenXmlConfiguration_WhenPropertisBeanInjected_thenBeanIsInitialized(){
        Bus bus = context.getBean("bus", Bus.class);

        assertNotNull(bus);
        assertNotNull(bus.getDriver());
    }
}
