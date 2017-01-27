package com.baeldung.java_bean_injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.java_bean_injection.*;

import org.springframework.context.ApplicationContext;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  


import junit.framework.TestResult;
import junit.framework.TestFailure;

import java.util.Enumeration;


/**
 * Bean Injection Test Application
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        TestResult result = new TestResult();
        AppTest.suite().run(result);
        System.out.println(String.format("Tests: %d",result.runCount()));
        System.out.println(String.format("Errors: %d",result.errorCount()));
        System.out.println(String.format("Failures: %d",result.failureCount()));
        if(result.failureCount() > 0){
            Enumeration<TestFailure> failures = result.failures();
            int failNum = 0;
            TestFailure failure = null;
            while(failures.hasMoreElements()){
                failure = failures.nextElement();
                
                System.out.println(failure.exceptionMessage());
                System.out.println(String.format("Test Failure %d\n",failNum));
                System.out.println(failure.trace());            
                System.out.print("\n");
            }
        }
       
    }
}
