package com.example.webflex.customer;


import com.github.javafaker.Faker;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private Mono<Customer> save(Customer customer) {
        log.debug("Request to save customer : {}", customer);
        return customerRepository.save(customer);
    }

    public void csvToDB() throws IOException {
        log.debug("Starting Database import");
        String csvFile = "/home/petro/faker/942856dd-9b73-4a6b-8097-a0ad833f640d.csv";

        Reader reader = Files.newBufferedReader(Paths.get(csvFile));
        CSVReader csvReader = new CSVReader(reader);

        String[] next;

        int x = 0;

        while (x < 10 && (next = csvReader.readNext()) != null) {
            log.debug("Found Record : \"{}, {}, {}\"", next[0], next[1], next[2]);
            Customer customer = new Customer();
            customer.setName(next[0]);
            customer.setPhoneNumber(next[2]);

            x++;
            save(customer);
        }

        csvReader.close();
    }

    public Flux<Customer> findAll() {
        log.debug("Request to get all customers");
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<Customer> customerFlux = Flux.fromStream(Stream.generate(this::randomCustomer));

        return Flux.zip(interval, customerFlux).map(Tuple2::getT2);
    }

    //        Flux<Customer> customerFlux = customerRepository.findAll();

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
