package com.baeldung.docker;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {
    private final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    
    @Autowired private CustomerRepository repository;
    private final Random random = new Random();
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        queryAllCustomers();
        createCustomer();
        queryAllCustomers();
    }

    private void createCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(this.generateRandomString());
        newCustomer.setLastName(this.generateRandomString());
        logger.info("Saving new customer...");
        this.repository.save(newCustomer);
    }

    private void queryAllCustomers() {
        List<Customer> allCustomers = this.repository.findAll();
        logger.info("Number of customers: " + (allCustomers != null ? allCustomers.size() : 0));
    }
    
    // see https://www.baeldung.com/java-random-string#java8-alphabetic
    private String generateRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        String generatedString = this.random.ints(leftLimit, rightLimit + 1)
          .limit(10)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
        String feedback =  generatedString.substring(0, 1).toUpperCase() + generatedString.substring(1);
        
        return feedback;
    }
}
