package com.bealdung.hexagonal.architecture.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bealdung.hexagonal.architecture.domain.service.DomainPersonService;
import com.bealdung.hexagonal.architecture.domain.service.PersonServicePort;
import com.bealdung.hexagonal.architecture.infrastructure.jpa.adapter.PersonJpaRepositoryAdpater;
import com.bealdung.hexagonal.architecture.infrastructure.jpa.repository.PersonRepository;

@Configuration
public class ApplicationConfiguration {

    private PersonJpaRepositoryAdpater personJpaRepository(PersonRepository personRepository) {
        return new PersonJpaRepositoryAdpater(personRepository);
    }

    @Bean
    PersonServicePort personService(PersonRepository personRepository) {
        return new DomainPersonService(personJpaRepository(personRepository));
    }
}