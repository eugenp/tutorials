package com.baeldung.serializeentityid;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SerializeEntityIdApp {

    private final PersonRepository repository;

    public SerializeEntityIdApp(PersonRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    void onStart() {
        final Person person1 = new Person();
        person1.setName("John Doe");
        final Person person2 = new Person();
        person2.setName("Markus Boe");

        this.repository.save(person1);
        this.repository.save(person2);
    }

    public static void main(String[] args) {
        SpringApplication.run(SerializeEntityIdApp.class, args);
    }

}
