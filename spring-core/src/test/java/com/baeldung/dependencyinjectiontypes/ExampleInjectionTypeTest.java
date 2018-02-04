package com.baeldung.dependencyinjectiontypes;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExampleInjectionTypeTest {
    
    private static ApplicationContext context ;
    
    @BeforeClass
    public static void setApplicationContext() {
        context = new ClassPathXmlApplicationContext("injectiontypes-example.xml") ;
    }

    @Test
    public void whenConstructorInjectionByIndex_thenSucessful() {
        ExampleInjection exampleInjection 
            = (ExampleInjection) context.getBean("exampleConstructorByIndex") ;
        
        assertEquals("Successfully injected based on index",exampleInjection.getStrField());
        assertEquals(100, exampleInjection.getIntField());
    }

    @Test
    public void whenConstructorInjectionByType_thenSucessful() {
        ExampleInjection exampleInjection 
            = (ExampleInjection) context.getBean("exampleConstructorByType") ;
        
        assertEquals("Successfully injected based on type",exampleInjection.getStrField());
        assertEquals(200, exampleInjection.getIntField());
    }

    @Test
    public void whenConstructorInjectionWithoutType_thenSucessful() {
        ExampleInjection exampleInjection 
            = (ExampleInjection) context.getBean("exampleConstructorWithoutType") ;
        
        assertEquals("300",exampleInjection.getStrField());
    }

    @Test
    public void whenSetterInjection_thenSucessful() {
        ExampleInjection exampleInjection 
            = (ExampleInjection) context.getBean("exampleSetter") ;
        
        assertEquals("Successfully injected from setter",exampleInjection.getStrField());
        assertEquals(400, exampleInjection.getIntField());
    }

    @AfterClass
    public static void closeApplicationContext() {
        ((ConfigurableApplicationContext)context).close() ;
    }
    
}
