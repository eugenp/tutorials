package com.baeldung.dependencyinjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("userbeaninjection-context.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.printUsers();
        UserManager userManager = context.getBean("userManager", UserManager.class);
        userManager.printUsers();
    }

}
