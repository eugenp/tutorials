package org.baeldung.dependency.injection;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectionUnitTest {
	private static ClassPathXmlApplicationContext context = null;
    private static final String CITY = "Chennai";
    private static final String COUNTRY = "India";
    private static final String NAME = "Martin";
    private static final int AGE = 32;
    
    @BeforeClass
    public static void setUp(){
    	context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    
    @AfterClass
    public static void tearDown(){
    	context.close();
    }

    @Test
    public void givenXmlConfigFile_whenUsingConstructorBasedBeanInjection_thenCorrectAddressDetails() {
    	
		Employee employee = (Employee)context.getBean("employeeConstructorInjection");

        Assert.assertEquals(CITY, employee.getAddress().getCity());
        Assert.assertEquals(COUNTRY, employee.getAddress().getCountry());
    }
    
    @Test
    public void givenXmlConfigFile_whenUsingConstructorResolution_thenCorrectNameAndAge() {
    	    
		Employee employee = (Employee)context.getBean("employeeConstructorResolution");

        Assert.assertEquals(NAME, employee.getName());
        Assert.assertEquals(AGE, employee.getAge());
        Assert.assertNull(employee.getDesignation());
    }
    
    @Test
    public void givenXmlConfigFile_whenUsingConstructorResolution_thenInCorrectAge() {
    	    
		Employee employee = (Employee)context.getBean("employeeConstructorMismatch");

        Assert.assertEquals(NAME, employee.getName());
        Assert.assertEquals(0, employee.getAge());
        Assert.assertEquals(String.valueOf(AGE), employee.getDesignation());
    }
    
    @Test
    public void givenXmlConfigFile_whenUsingSetterBasedBeanInjection_thenCorrectAddressDetails() {
    	
		Employee employee = (Employee)context.getBean("employeeSetterInjection");

        Assert.assertEquals(CITY, employee.getAddress().getCity());
        Assert.assertEquals(COUNTRY, employee.getAddress().getCountry());
    }
}
