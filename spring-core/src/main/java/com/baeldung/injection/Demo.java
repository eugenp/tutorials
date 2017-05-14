package com.baeldung.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        UserService bean = context.getBean(UserService.class);

        System.out.println(bean);

        //Thanks to setter can changed implementation
        bean.setUserDAO(new UserDAO());
    }
}
