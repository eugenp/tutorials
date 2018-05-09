package com.baeldung;

import com.baeldung.controller.repository.AddressAvailabilityRepository;
import com.baeldung.controller.repository.AddressRepository;
import com.baeldung.controller.repository.UserRepository;
import com.baeldung.entity.Address;
import com.baeldung.entity.AddressAvailability;
import com.baeldung.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication @EntityScan("com.baeldung.entity") @EnableJpaRepositories("com.baeldung.controller.repository")
@EnableAutoConfiguration public class Application {

    @Autowired private UserRepository personRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private AddressAvailabilityRepository addressAvailabilityRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct private void initializeData() {
        // Create John
        final AddressAvailability addressOneAvailability = new AddressAvailability(true, true, false, false, false, true, true);
        addressAvailabilityRepository.save(addressOneAvailability);
        final User john = new User("John");
        personRepository.save(john);
        final Address addressOne = new Address("Fake Street 1", "Fake Country", addressOneAvailability, john);
        addressRepository.save(addressOne);
        // Create Lisa
        final AddressAvailability addressTwoAvailability = new AddressAvailability(false, false, false, false, false, true, true);
        addressAvailabilityRepository.save(addressTwoAvailability);
        final User lisa = new User("Lisa");
        personRepository.save(lisa);
        final Address addressTwo = new Address("Real Street 1", "Real Country", addressTwoAvailability, lisa);
        addressRepository.save(addressTwo);
    }
}
