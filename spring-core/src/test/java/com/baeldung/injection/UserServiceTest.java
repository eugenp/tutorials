package com.baeldung.injection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Test
    public void shouldInjectUserDAO(){
        Assert.assertNotNull(userService.getUserDAO());
    }

    @Test
    public void shouldSetNewUserDAO(){
        UserDAO unexpected = userService.getUserDAO();

        userService.setUserDAO(new UserDAO());

        Assert.assertNotEquals(unexpected,userService.getUserDAO());
    }
}