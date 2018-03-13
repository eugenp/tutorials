package com.examples;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class JavaConfigExample {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfigExample.class);
        Helloworld helloworld = context.getBean(Helloworld.class);
        helloworld.printHelloWorld();
//        helloworld.printMessage();
        context.close();
    }

    @Bean
    public Helloworld getHelloworld() {
        return new Helloworld();
    }

    @Bean
    public AroundAdvice getAspect() {
        return new AroundAdvice();
    }

    @Bean
    public BeforeAdvice getBeforeAspect() {
        return new BeforeAdvice();
    }

    @Bean
    public AfterAdvice getAfterAspect() {
        return new AfterAdvice();
    }
}
