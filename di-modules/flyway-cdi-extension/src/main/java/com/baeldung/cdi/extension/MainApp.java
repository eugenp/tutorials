package com.baeldung.cdi.extension;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

@ApplicationScoped
@DataSourceDefinition(name = "ds", className = "org.h2.Driver", url = "jdbc:h2:mem:testdb")
public class MainApp {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        try (SeContainer container = initializer.initialize()) {
        }
    }
}