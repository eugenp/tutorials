package com.baeldung.dataloading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import com.baeldung.boot.Application;

@SpringBootApplication(scanBasePackages = { "com.baeldung.dataloading" })
public class ApplicationDataLoading {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "datasource");

        applicationContext = SpringApplication.run(Application.class, args);
    }

}
