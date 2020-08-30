package com.baeldung.springbootlogging;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    private final static Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/")
    public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }

    private static final org.apache.logging.log4j.Logger loggerNative = LogManager.getLogger(LoggingController.class);

    @GetMapping("/native")
    public String nativeLogging() {
        loggerNative.trace("This TRACE message has been printed by Log4j2 without passing through SLF4J");
        loggerNative.debug("This DEBUG message has been printed by Log4j2 without passing through SLF4J");
        loggerNative.info("This INFO message has been printed by Log4j2 without passing through SLF4J");
        loggerNative.warn("This WARN message been printed by Log4j2 without passing through SLF4J");
        loggerNative.error("This ERROR message been printed by Log4j2 without passing through SLF4J");
        loggerNative.fatal("This FATAL message been printed by Log4j2 without passing through SLF4J");
        return "Howdy! Check out the Logs to see the output printed directly through Log4j2...";
    }
}
