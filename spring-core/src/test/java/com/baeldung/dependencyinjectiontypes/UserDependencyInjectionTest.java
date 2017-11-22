package com.baeldung.dependencyinjectiontypes;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

public class UserDependencyInjectionTest {

    @Test
    public void whenUserServiceGetsUsers_thenUsersAreReturned() {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
        UserService userService = context.getBean(UserService.class);
        assertEquals("John, Sarah, Mike", userService.getUsers());
    }

    @Test
    public void whenUserManagerGetsUsers_thenUsersAreReturned() {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
        UserManager userManager = context.getBean(UserManager.class);
        assertEquals("John, Sarah, Mike", userManager.getUsers());
    }

}
