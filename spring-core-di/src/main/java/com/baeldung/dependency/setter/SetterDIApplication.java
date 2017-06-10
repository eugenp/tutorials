package com.baeldung.dependency.setter;

import com.baeldung.dependency.domain.User;
import com.baeldung.dependency.service.IUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.dependency.setter", "com.baeldung.dependency.repository" })
public class SetterDIApplication {

    private static ConfigurableApplicationContext ctx;

    public static void main(final String[] args) {
        ctx = SpringApplication.run(SetterDIApplication.class, args);

        IUserService userService = ctx.getBean(IUserService.class);

        User user = new User();
        user.setId(2);
        user.setName("Ivan");

        userService.createUser(user);
        System.out.println(user);

    }

    @PreDestroy
    public void clearContext() {
        ctx.close();
    }

}
