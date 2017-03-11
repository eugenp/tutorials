package com.baeldung;

import com.baeldung.controller.EmployeeController;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ConstructorInjectionTest extends BaseTest {

    private Logger logger = LoggerFactory.getLogger(ConstructorInjectionTest.class);

    @Autowired
    EmployeeController employeeController;

    @Test
    public void whenEmployeeServiceInjectedViaConstructorInjection_thenSuccessful(){
        logger.info("Testing constructor injection....");

        Assert.assertNotNull(employeeController);
        Assert.assertNotNull(employeeController.getEmployeeService());
        logger.info("Constructor injection test is Successful!");
    }
}
