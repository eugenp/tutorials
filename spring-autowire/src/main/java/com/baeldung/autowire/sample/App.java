package com.baeldung.autowire.sample;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.injection.Square;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        FooService fooService = ctx.getBean(FooService.class);
        fooService.doStuff();

        Square mainObj = ctx.getBean(Square.class);

        mainObj.draw();
    }
}
