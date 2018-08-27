package com.baeldung.tomcatconnectionpool.runners;

import com.baeldung.tomcatconnectionpool.entities.Customer;
import com.baeldung.tomcatconnectionpool.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineCrudRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandLineCrudRunner.class);
    private final CustomerRepository repository;
    
    @Autowired
    public CommandLineCrudRunner(CustomerRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        repository.save(new Customer("John", "Doe"));
        repository.save(new Customer("Jennifer", "Wilson"));
        
        logger.info("Customers found with findAll():");
        repository.findAll().forEach(c -> logger.info(c.toString()));
        
        logger.info("Customer found with findById(1L):");
        Customer customer = repository.findById(1L)
                .orElseGet(() -> new Customer("Non-existing customer", ""));
        logger.info(customer.toString());
        
        logger.info("Customer found with findByLastName('Wilson'):");
        repository.findByLastName("Wilson").forEach(c -> {
            logger.info(c.toString());
        });
    }
}
