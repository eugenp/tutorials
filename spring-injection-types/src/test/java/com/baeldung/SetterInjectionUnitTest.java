package com.baeldung;

import com.baeldung.controller.UserController;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SetterInjectionUnitTest extends BaseTest{

    private Logger logger = LoggerFactory.getLogger(SetterInjectionUnitTest.class);

    @Autowired
    private UserController userController;

    @Test
    public void whenUserServiceInjectedAsSetterInjection_thenSuccessful(){
        logger.info("Testing setter injection....");

        Assert.assertNotNull(userController);
        Assert.assertNotNull(userController.getUserService());
        logger.info("Setter injection test is Successful!");
    }
}
