package com.baeldung.componentscan.springbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.componentscan.ExampleBean;
import com.baeldung.componentscan.springbootapp.flowers.Rose;

@SpringBootApplication
//@ComponentScan(basePackages = "com.baeldung.componentscan.springbootapp.animals")
//@ComponentScan ( excludeFilters = @ComponentScan.Filter(type=FilterType.REGEX,pattern="com\\.baeldung\\.componentscan\\.springbootapp\\.flowers\\..*"))
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Rose.class))

public class SpringBootComponentScanApp {
    private static ApplicationContext applicationContext;

    @Bean
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootComponentScanApp.class, args);
        checkBeansPresence("cat", "dog", "rose", "exampleBean", "springBootApp");

    }

    private static void checkBeansPresence(String... beans) {
        for (String beanName : beans) {
            System.out.println("Is " + beanName + " in ApplicationContext: " + applicationContext.containsBean(beanName));
        }
    }
}
