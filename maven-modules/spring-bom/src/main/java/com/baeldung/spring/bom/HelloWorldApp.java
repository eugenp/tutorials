package com.baeldung.spring.bom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldApp {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfig.class);
        HelloWorldBean helloWorldBean = ctx.getBean(HelloWorldBean.class);
        System.out.println(helloWorldBean.sayHello());
    }

}
