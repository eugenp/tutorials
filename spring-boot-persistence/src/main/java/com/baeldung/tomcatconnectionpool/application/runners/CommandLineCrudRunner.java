package com.baeldung.tomcatconnectionpool.application.runners;

import com.baeldung.tomcatconnectionpool.application.entities.Customer;
<<<<<<< HEAD
import com.baeldung.tomcatconnectionpool.repositories.CustomerRepository;
=======
import com.baeldung.tomcatconnectionpool.application.repositories.CustomerRepository;
>>>>>>> 9220c0538c338db616bfe46a5bef0d036d6a559c
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineCrudRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandLineCrudRunner.class);
    
    @Autowired
    CustomerRepository repository;
    
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
