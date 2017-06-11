package org.baeldung.dependency.injection;

import org.baeldung.dependency.injection.config.AppConfig;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionUnitTest {
	private static AnnotationConfigApplicationContext context = null;
    private static final String CITY = "Chennai";
    private static final String COUNTRY = "India";
    
    @BeforeClass
    public static void setUp(){
    	context = new AnnotationConfigApplicationContext(AppConfig.class);
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
    public void givenXmlConfigFile_whenUsingSetterBasedBeanInjection_thenCorrectAddressDetails() {
    	
		Employee employee = (Employee)context.getBean("employeeSetterInjection");

        Assert.assertEquals(CITY, employee.getAddress().getCity());
        Assert.assertEquals(COUNTRY, employee.getAddress().getCountry());
    }
}
