package com.baeldung.graylog;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraylogDemoApplication {

    private final static Logger LOG =
            Logger.getLogger(GraylogDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GraylogDemoApplication.class, args);
        LOG.info("Hello from Spring Boot");
    }
}
