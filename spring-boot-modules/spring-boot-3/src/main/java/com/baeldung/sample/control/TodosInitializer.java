package com.baeldung.sample.control;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodosInitializer {

    private final TodosService service;
    /*
     * we cannot use @Profile("default") because
     * we are not able inject the bean during test
     * depending from the profile activation
     */
    private final DataInitializationConfigurationData config;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeTodos() {
        if (this.config.isInitializeOnStartup() && this.service.count() < 1) {
            this.service.create(new Todo(null, "Deploy and run the application."));
            this.service.create(new Todo(null, "Enter some TODO items!"));
        }
    }

}
