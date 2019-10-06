package com.baeldung.batch;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class BatchTestConfiguration {

    private static Logger LOGGER = LoggerFactory.getLogger(BatchTestConfiguration.class);
    
    @PostConstruct
    public void setUp() {
        Properties properties = System.getProperties();
        properties.setProperty("file.input", "src/test/resources/test-input-one.csv");
        LOGGER.info("Setting up property file.input");
    }
}
