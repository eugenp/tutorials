package com.baeldung.dependency.constructor;

import com.baeldung.dependency.domain.User;
import com.baeldung.dependency.service.IUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.dependency.constructor", "com.baeldung.dependency.repository" })
public class ConstructorDIApplication {

    private static ConfigurableApplicationContext ctx;

    public static void main(final String[] args) {
        ctx = SpringApplication.run(ConstructorDIApplication.class, args);

        IUserService userService = ctx.getBean(IUserService.class);

        User user = new User();
        user.setId(1);
        user.setName("John");

        userService.createUser(user);
        System.out.println(user);

    }

    @PreDestroy
    public void clearContext() {
        ctx.close();
    }

}
