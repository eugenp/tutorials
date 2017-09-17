package com.baeldung.di.constructorbased;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.constructorbased.domain.MessageProcessor;

public class SpringRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        System.out.println(context.getBean(MessageProcessor.class));

    }

}
