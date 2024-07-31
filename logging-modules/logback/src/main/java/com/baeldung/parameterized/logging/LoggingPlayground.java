package com.baeldung.parameterized.logging;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingPlayground {

    public static final Logger log = LoggerFactory.getLogger(LoggingPlayground.class);

    public static void main(String[] args) {
        log.info("App is running at {}", LocalDateTime.now());

        log.info("App is running at {}, zone = {}", LocalDateTime.now(), ZonedDateTime.now()
            .getZone());

        log.info("App is running at {}, zone = {}, java version = {}", LocalDateTime.now(), ZonedDateTime.now()
            .getZone(), System.getProperty("java.version"));

        log.info("App is running at {}, zone = {}, java version = {}, java vm = {}", LocalDateTime.now(), ZonedDateTime.now()
            .getZone(), System.getProperty("java.version"), System.getProperty("java.vm.name"));

        //old approach to print multiple parameters
        log.info("App is running at {}, zone = {}, java version = {}, java vm = {}",
            new Object[] { ZonedDateTime.now(), ZonedDateTime.now().getZone(), System.getProperty("java.version"), System.getProperty("java.vm.name") });

        Exception exceptionCause = new Exception(new IllegalArgumentException("Something unprocessable"));

        //exception as last parameters is considered as exception and printed with trace
        log.info("App is running at {}, zone = {}, java version = {}, java vm = {}", LocalDateTime.now(), ZonedDateTime.now()
            .getZone(), System.getProperty("java.version"), System.getProperty("java.vm.name"), exceptionCause);

        //exception in between parameters is considered as pure parameter and printed without trace
        log.info("App is running at {}, zone = {}, java version = {}, java vm = {}", LocalDateTime.now(), ZonedDateTime.now()
            .getZone(), System.getProperty("java.version"), exceptionCause, System.getProperty("java.vm.name"));

    }
}
