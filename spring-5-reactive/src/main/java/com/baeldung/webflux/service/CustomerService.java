package com.baeldung.webflex.service;


import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;


import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    public Flux<Customer> findAll() {
        log.debug("Request to get all customers");
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<Customer> customerFlux = Flux.fromStream(Stream.generate(this::randomCustomer));

        return Flux.zip(interval, customerFlux).map(Tuple2::getT2);
    }

    private Customer randomCustomer() {
        Faker faker = new Faker();

        String name = faker.name().fullName();

        String number = "2547" + faker.number().digits(8);

        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        Customer customer = new Customer();
        customer.setPhoneNumber(number);
        customer.setName(name);
        customer.setBirthDate(randomDate);
        customer.setId(UUID.randomUUID().toString());
        return customer;

    }


}
