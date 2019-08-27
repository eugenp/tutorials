package com.baeldung.patterns.hexagonal;

import com.baeldung.patterns.hexagonal.driver.ConsoleDriver;
import com.baeldung.patterns.hexagonal.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {
    
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        ConsoleDriver driver = injector.getInstance(ConsoleDriver.class);
        driver.generateUser();
    }
    
}
