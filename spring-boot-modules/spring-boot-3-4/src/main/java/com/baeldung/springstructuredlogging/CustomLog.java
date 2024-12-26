package com.baeldung.springstructuredlogging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomLog implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLog.class);

    public void additionalDetailsWithMdc() {
        MDC.put("userId", "1");
        MDC.put("userName", "Baeldung");
        LOGGER.info("Hello structured logging!");
        MDC.remove("userId");
        MDC.remove("userName");
    }

    public void additionalDetailsUsingFluentApi() {
        LOGGER.atInfo()
            .setMessage("Hello Structure logging!")
            .addKeyValue("userId", "1")
            .addKeyValue("userName", "Baeldung")
            .log();
    }

    @Override
    public void run(String... args) {
        additionalDetailsWithMdc();
        additionalDetailsUsingFluentApi();
    }
}
