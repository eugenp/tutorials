package com.baeldung.spring.jpa.guide;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.spring.jpa.guide.model.Publishers;
import com.baeldung.spring.jpa.guide.repository.PublisherRepository;

@SpringBootApplication
public class JpaGuideApp {

    public static void main(String[] args) {
        SpringApplication.run(JpaGuideApp.class, args);
    }

    @Bean
    public CommandLineRunner demo(PublisherRepository publisherRepository) {
        return (args) -> {
            publisherRepository.save(new Publishers("Spring", "Dallas", 10));
        };
    }

}
