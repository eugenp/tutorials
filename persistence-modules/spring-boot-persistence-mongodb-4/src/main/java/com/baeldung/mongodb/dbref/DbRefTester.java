package com.baeldung.mongodb.dbref;

import com.baeldung.mongodb.dbref.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
