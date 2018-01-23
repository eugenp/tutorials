package com.baeldung.dependencyinjection.setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DependencyInjectionBySetterApplication {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyInjectionBySetterApplication.class);


    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(DependencyInjectionBySetterApplication.class, args);
        BinarySearch bean = applicationContext.getBean(BinarySearch.class);
        bean.binarySearch(new int[] { 1, 2, 3 }, 3);
    }
}
