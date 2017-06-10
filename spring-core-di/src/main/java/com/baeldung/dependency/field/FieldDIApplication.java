package com.baeldung.dependency.field;

import com.baeldung.dependency.domain.User;
import com.baeldung.dependency.service.IUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.dependency.field", "com.baeldung.dependency.repository" })
public class FieldDIApplication {

    private static ConfigurableApplicationContext ctx;

    public static void main(final String[] args) {
        ctx = SpringApplication.run(FieldDIApplication.class, args);

        IUserService userService = ctx.getBean(IUserService.class);

        User user = new User();
        user.setId(3);
        user.setName("Juan");

        userService.createUser(user);
        System.out.println(user);

    }

    @PreDestroy
    public void clearContext() {
        ctx.close();
    }

}
