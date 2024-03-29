package com.baeldung.globaltimezone;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        // TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
        logger.info("Default timezone, before main run, is set to: " + TimeZone.getDefault()
            .getDisplayName());

        SpringApplication.run(MainApplication.class, args);
    }

    @PostConstruct
    public void executeAfterMain() {
        // TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
        logger.info("Default timezone, after main run, is set to: " + TimeZone.getDefault()
            .getDisplayName());
    }
}
