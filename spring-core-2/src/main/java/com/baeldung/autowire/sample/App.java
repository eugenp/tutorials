package com.baeldung.autowire.sample;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            FooService fooService = ctx.getBean(FooService.class);
            fooService.doStuff();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
