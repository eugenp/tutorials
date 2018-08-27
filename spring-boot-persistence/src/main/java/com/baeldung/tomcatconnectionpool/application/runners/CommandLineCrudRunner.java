package com.baeldung.tomcatconnectionpool.application.runners;

import com.baeldung.tomcatconnectionpool.application.entities.Customer;
import com.baeldung.tomcatconnectionpool.application.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class CommandLineCrudRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandLineCrudRunner.class);
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CommandLineCrudRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        customerRepository.save(new Customer("John", "Doe"));
        customerRepository.save(new Customer("Jennifer", "Wilson"));
        
        logger.info("Customers found with findAll():");
        customerRepository.findAll().forEach(c -> logger.info(c.toString()));
        
        logger.info("Customer found with findById(1L):");
        Customer customer = customerRepository.findById(1L)
                .orElseGet(() -> new Customer("Non-existing customer", ""));
        logger.info(customer.toString());
        
        logger.info("Customer found with findByLastName('Wilson'):");
        customerRepository.findByLastName("Wilson").forEach(c -> {
            logger.info(c.toString());
        });
    }
}
