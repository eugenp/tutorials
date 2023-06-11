package com.baeldung.extensions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-log4j2-extensions.properties")
public class SpringBootLog4j2ExtensionsApplication {

    private static final Logger logger = LogManager.getLogger(SpringBootLog4j2ExtensionsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLog4j2ExtensionsApplication.class, args);

        logger.trace("Trace log message");
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
        logger.warn("Warn log message");
        logger.fatal("Fatal log message");
    }
}
