package com.baeldung.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ActiveProfiles("log-group")
public class LogGroupApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogGroupApplication.class);

    @RequestMapping("/log-group")
    public void justLog() {
        LOGGER.debug("Received a request");
    }

    public static void main(String[] args) {
        SpringApplication.run(LogGroupApplication.class, args);
    }
}
