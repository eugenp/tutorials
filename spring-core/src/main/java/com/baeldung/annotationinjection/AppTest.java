package com.baeldung.java_bean_injection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.java_bean_injection.*;

import java.io.File;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class AppTest  extends TestCase{
   /**
   * Create the test case
   *
   * @param testName name of the test case
   */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
    * @return the suite of tests being tested
    */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
    * Rigourous Test :-)
    */
    public void testApp()
    {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
        InjectedClass injectedClass = (InjectedClass) context.getBean(InjectedClass.class);
        assertTrue(injectedClass.getMyInt() == 10);
        assertTrue(injectedClass.getTestString().equals("test"));
        assertNotNull(injectedClass.obj);
        assertTrue(injectedClass.myConstructorArg.equals("test"));
    }
}
