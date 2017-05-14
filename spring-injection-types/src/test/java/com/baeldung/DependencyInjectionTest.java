package com.baeldung;

import com.baeldung.controller.EmployeeController;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DependencyInjectionTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(DependencyInjectionTest.class);

    @Autowired
    EmployeeController employeeController;

    @Test
    public void whenEmployeeServiceInjectedViaConstructorInjection_thenSuccessful(){
        logger.info("Testing constructor injection....");

        Assert.assertNotNull(employeeController);
        Assert.assertNotNull(employeeController.getEmployeeService());
        logger.info("Constructor dependency injection test is Successful!");
    }

    @Test
    public void whenAddressFieldInjectedViaSetterInjection_thenSuccessful(){
        logger.info("Testing constructor injection....");
        Assert.assertNotNull(employeeController);
        Assert.assertNotNull(employeeController.getEmployeeService());
        Assert.assertNotNull(employeeController.getEmployeeService().getEmployee());
        Assert.assertNotNull(employeeController.getEmployeeService().getEmployee().getAddress());
        Assert.assertEquals(employeeController.getEmployeeService().getEmployee().getAddress().getCountry(), "USA");
        Assert.assertEquals(employeeController.getEmployeeService().getEmployee().getAddress().getState(), "CA");
        Assert.assertEquals(employeeController.getEmployeeService().getEmployee().getAddress().getCity(), "ABC");
        Assert.assertEquals(employeeController.getEmployeeService().getEmployee().getAddress().getStreet(), "Street 1");
        Assert.assertEquals(employeeController.getEmployeeService().getEmployee().getAddress().getZipcode(), "94522");
        logger.info("Setter dependency injection test is Successful!");
    }
}
