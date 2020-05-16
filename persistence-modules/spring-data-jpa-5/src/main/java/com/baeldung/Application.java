package com.baeldung;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.baeldung.model.Customer;
import com.baeldung.model.CustomerDto;
import com.baeldung.service.CustomerService;

@SpringBootApplication @EnableCaching
public class Application {

    @Autowired CustomerService service;
    @Autowired CacheManager cacheManager;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean public CommandLineRunner commandLineRunner(ApplicationContext ctx) throws Exception {        
        logger.info("-- BASIC LOAD AND SAVE --");
        basicLoadAndSave();
        logger.info("-- BASIC LOAD AND SAVE + MAPPER --");
        basicLoadAndSaveWithMapper();
        return null;
    }

    private void basicLoadAndSave() {
        Customer myCustomer = service.addCustomer("John");
        logger.info("Insert -- " + myCustomer.toString());
        myCustomer = service.updateCustomer(myCustomer.id, "+00");
        logger.info("Update -- " + myCustomer.toString());
    }
    
    private void basicLoadAndSaveWithMapper() {
        CustomerDto dto = new CustomerDto(null);
        dto.name = "Johnny";
        Customer entity = service.addCustomer(dto);
        logger.info("Insert -- " + entity.toString());
        CustomerDto dto2 = new CustomerDto(entity.id);
        dto2.phone = "+44";
        entity = service.updateCustomer(dto2);
        logger.info("Update -- " + entity.toString());
    }

}
