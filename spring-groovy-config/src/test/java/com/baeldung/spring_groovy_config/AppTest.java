package com.baeldung.spring_groovy_config;

import com.baeldung.spring_groovy_config.*;
import groovy.lang.Binding;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
/**
 * Spring Framework Tests for Groovy.
 */
public class AppTest{
    
    @Test
    public void testSimple(){
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/groovyContextWithConstructor.groovy");
        TestClass test = (TestClass) context.getBean("testClass");
        assertNotNull(test.getTestString());
        assertEquals(test.getTestString(),"Test String");
        assertTrue(test.getTestDouble() == 10.2);
        
        String testString = context.getBean("testString",String.class);
        assertEquals(testString,"Test String");
    }
    
    
    @Test
    public void testProperties(){
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/groovyPropConfig.groovy");
        TestClassB test = (TestClassB) context.getBean("testClassB");
        assertNotNull(test.getTestStringB());
        assertEquals(test.getTestStringB(),"Test String");
        assertTrue(test.getTestIntB() == 10);
    }
    
    @Test
    public void testPropertiesWithClosure(){
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/groovyPropConfigWithClosure.groovy");
        TestClassB test = (TestClassB) context.getBean("testClassB");
        assertNotNull(test.getTestStringB());
        assertEquals(test.getTestStringB(),"Test String");
        assertTrue(test.getTestIntB() == 10);
    }
    
    
    @Test
    public void testWithRef(){
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/groovyTestWithRefBean.groovy");
        ClassWithRef test = (ClassWithRef) context.getBean("classWithRef");
        assertEquals(test.getMyClass().getGroovyInt(),5);
    }
}
