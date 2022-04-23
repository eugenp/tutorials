package com.baeldung.mongodb.dbref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.baeldung.mongodb.dbref.repository.PersonRepository;

@Component
public class DbRefTester implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DbRefTester.class);
    
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("{}", personRepository.findAll());

    }

}
