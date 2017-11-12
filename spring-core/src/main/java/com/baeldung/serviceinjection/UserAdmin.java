package com.baeldung.serviceinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserAdmin {

    public static void main(String... args) {
        List<User> users;
        ApplicationContext ctx = new ClassPathXmlApplicationContext("serviceinjection-context.xml");

        // Constructor-based injection at work
        UserServiceA constructorBased = ctx.getBean("userServiceA", UserServiceA.class);
        users = constructorBased.getAllUsers();
        System.out.println("== Constructor-based injection. ==");
        System.out.println("\tFirst user is: " + users.get(0).getName());

        UserServiceB setterBased = ctx.getBean("userServiceB", UserServiceB.class);
        users = setterBased.getAllUsers();
        System.out.println("== Setter-based injection. ==");
        System.out.println("\tFirst user is: " + users.get(0).getName());
    }
}
