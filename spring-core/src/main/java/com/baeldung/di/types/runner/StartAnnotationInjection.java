package com.baeldung.di.types.runner;

import com.baeldung.di.types.annotation.School;
import com.baeldung.di.types.config.DIConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;

public class StartAnnotationInjection {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DIConfig.class);

        School department = context.getBean(School.class);
        Objects.requireNonNull(department);
        System.out.println(department);

    }
}
