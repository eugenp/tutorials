package baeldung.java_bean_injection;

import baeldung.java_bean_injection.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  

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
        ApplicationContext context=new ClassPathXmlApplicationContext("injection.xml");  
        InjectedClass injectedClass = (InjectedClass) context.getBean("injection");
        assertTrue(injectedClass.wiredObject.getMyWiredString().equals("test"));
        assertTrue(injectedClass.myConstructorLong == 100);
        assertTrue(injectedClass.myConstructorDouble == 1);
        assertTrue(injectedClass.myConstructorString.equals("test"));
        assertTrue(injectedClass.constructorObject != null);
        assertTrue(injectedClass.getInjectedObject() != null);
        assertTrue(injectedClass.getMyInt() == 0);
        assertTrue(injectedClass.getMyString().equals("test"));
    }
}
