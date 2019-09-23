package com.baeldung.hexagonal;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class HexagonalApp {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance()
                .disableDiscovery()
                .addBeanClasses(Resources.class)
                .addBeanClasses(PersonRegistry.class)
                .initialize()) {
            // start the container, retrieve a bean and do work with it
            final PersonRegistry personRegistry = container

                    .select(PersonRegistry.class)
                    .get();
            final Person input = new Person();
            input.setName("John");
            input.setSurname("Doe");
            personRegistry.createPerson(input);

        }
    }

}
