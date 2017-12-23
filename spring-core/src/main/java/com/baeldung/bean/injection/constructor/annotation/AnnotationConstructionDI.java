package com.baeldung.bean.injection.constructor.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConstructionDI {

    public static void main(String[] args) {
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        User user = context.getBean("user", User.class);
        
        System.out.println(user.toString());
    }

}
