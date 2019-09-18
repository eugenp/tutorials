package com.baeldung.componentscan.filter.aspectj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.Arrays;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ,
        pattern = "com.baeldung.componentscan.filter.aspectj.* "
                + "&& !(com.baeldung.componentscan.filter.aspectj.L* "
                + "|| com.baeldung.componentscan.filter.aspectj.E*)"))
public class ComponentScanCustomFilterApp {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = new AnnotationConfigApplicationContext(ComponentScanCustomFilterApp.class);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(System.out::println);
    }
}
