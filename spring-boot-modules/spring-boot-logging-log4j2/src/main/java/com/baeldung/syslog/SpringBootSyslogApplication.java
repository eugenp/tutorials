package com.baeldung.syslog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSyslogApplication {

    private static final Logger logger = LogManager.getLogger(SpringBootSyslogApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSyslogApplication.class, args);

        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
        logger.warn("Warn log message");
        logger.fatal("Fatal log message");
        logger.trace("Trace log message");
    }
}
