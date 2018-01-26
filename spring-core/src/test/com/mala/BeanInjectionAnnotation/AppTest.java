package com.mala.BeanInjectionAnnotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mala.DI.config.Config;
import com.mala.DI.config.Config2;
import com.mala.DI.config.Config3;
import com.mala.DI.model.Patient;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
    
	public AppTest(String testName) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void test_PatientBeanInjection_with_Config() {
    	ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    	Patient patient = context.getBean(Patient.class);
    	assertEquals("FastAspirin", patient.getMedicine().toString());
    }
    	
    public void test_PatientBeanInjection_with_Config2() {
    	ApplicationContext context = new AnnotationConfigApplicationContext(Config2.class);
    	Patient patient = context.getBean(Patient.class);
    	assertEquals("Paracetamol", patient.getMedicine().toString());
    }	
    	
    public void test_PatientBeanInjection_with_Config3() {
    	ApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
    	Patient patient = context.getBean(Patient.class);
    	assertEquals("Levolin.63", patient.getMedicine().toString());
    }    
}
