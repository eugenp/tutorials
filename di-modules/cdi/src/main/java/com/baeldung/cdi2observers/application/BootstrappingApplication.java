package com.baeldung.cdi2observers.application;

import com.baeldung.cdi2observers.events.ExampleEvent;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.inject.spi.BeanManager;

public class BootstrappingApplication {
    
    public static void main(String... args) {
        SeContainerInitializer containerInitializer = SeContainerInitializer.newInstance();
        try (SeContainer container = containerInitializer.initialize()) {
            BeanManager beanManager = container.getBeanManager();
            Event<ExampleEvent> event = (Event<ExampleEvent>) beanManager.createInstance().select(ExampleEvent.class).get();
            event.fire(new ExampleEvent("Welcome to Baeldung!"));
        }
    }
}
