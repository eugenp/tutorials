package com.baeldung.hexagonal_example.domain;

import com.baeldung.hexagonal_example.domain.ports.ContactRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class ApplicationConfiguration {

    public ContactRepository testContactRepository() {
        return new FakeContactRepository();
    }

    @Bean
    @Profile("production")
    ContactApplication contactApplication(ContactRepository repository) {
        return new ContactApplication(repository);
    }

}
