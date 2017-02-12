package com.baeldung.autowire.sample;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CalcApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Calculator calc = ctx.getBean(Calculator.class);
        calc.calculate(5);
    }
}
