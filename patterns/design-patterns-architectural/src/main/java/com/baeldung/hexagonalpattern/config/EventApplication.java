package com.baeldung.hexagonalpattern.config;

import com.baeldung.hexagonalpattern.repositories.EventFileRepository;
import com.baeldung.hexagonalpattern.repositories.EventRepository;
import com.baeldung.hexagonalpattern.services.EventPersister;
import com.baeldung.hexagonalpattern.services.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.WebApplicationType.SERVLET;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.hexagonalpattern.*")
public class EventApplication {

    @Value("${events.file-path}")
    private String eventsFilePath;

    public static void main(String[] args) {
        new SpringApplicationBuilder(EventApplication.class).web(SERVLET).run(args);
    }

    @Bean
    public EventRepository eventRepository() {
        return new EventFileRepository(this.eventsFilePath);
    }

    @Bean
    public EventPersister eventPersister(EventRepository eventRepository) {
        return new EventService(eventRepository);
    }
}
