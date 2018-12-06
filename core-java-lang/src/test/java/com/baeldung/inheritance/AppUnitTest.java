package com.baeldung.inheritance;

import com.baeldung.inheritance.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppUnitTest extends TestCase {
    
	public AppUnitTest(String testName) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite(AppUnitTest.class);
    }

    @SuppressWarnings("static-access")
	public void testStaticMethodUsingBaseClassVariable() {
    	Car first = new ArmoredCar();
    	assertEquals("Car", first.msg());
    }
    	
    @SuppressWarnings("static-access")
	public void testStaticMethodUsingDerivedClassVariable() {
    	ArmoredCar second = new ArmoredCar();
    	assertEquals("ArmoredCar", second.msg());
    }
    
    public void testAssignArmoredCarToCar() {
    	Employee e1 = new Employee("Shreya", new ArmoredCar());
    	assertNotNull(e1.getCar());
    }

    public void testAssignSpaceCarToCar() {
    	Employee e2 = new Employee("Paul", new SpaceCar());
    	assertNotNull(e2.getCar());
    }

    public void testBMWToCar() {
    	Employee e3 = new Employee("Pavni", new BMW());
    	assertNotNull(e3.getCar());
    }

}
