package com.baeldung.bean.injection.setter.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationSetterDI {

    public static void main(String[] args) {
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        User user = context.getBean("user", User.class);
        
        System.out.println(user.toString());
    }

}
