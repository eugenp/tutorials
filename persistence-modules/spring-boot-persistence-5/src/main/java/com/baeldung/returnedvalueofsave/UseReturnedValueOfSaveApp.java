package com.baeldung.returnedvalueofsave;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UseReturnedValueOfSaveApp {

    public static final Logger log = LoggerFactory.getLogger(UseReturnedValueOfSaveApp.class);

    public static void main(String[] args) {
        SpringApplication.run(UseReturnedValueOfSaveApp.class, args);
    }
}