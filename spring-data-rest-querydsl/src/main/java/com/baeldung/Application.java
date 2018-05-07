package com.baeldung;

import com.baeldung.controller.repository.AddressRepository;
import com.baeldung.controller.repository.PersonRepository;
import com.baeldung.entity.Address;
import com.baeldung.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication @EntityScan("com.baeldung.entity") @EnableJpaRepositories("com.baeldung.controller.repository")
public class Application {

    @Autowired private PersonRepository personRepository;
    @Autowired private AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct private void initializeData() {
        // Create John
        final Address johnsAddress = new Address(UUID.randomUUID().toString(), "Fake Street 1", "Fake Country");
        addressRepository.save(johnsAddress);
        final Person john = new Person(UUID.randomUUID().toString(), "John", johnsAddress);
        personRepository.save(john);

        // Create Lisa
        final Address lisasAddress = new Address(UUID.randomUUID().toString(), "Real Street 1", "Real Country");
        addressRepository.save(lisasAddress);
        final Person lisa = new Person(UUID.randomUUID().toString(), "Lisa", lisasAddress);
        personRepository.save(lisa);
    }
}
