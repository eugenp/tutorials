package com.baeldung.customloglevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log")
    public String generateLogs() {
        logger.trace("This is a TRACE message from controller.");
        logger.debug("This is a DEBUG message from controller.");
        logger.info("This is an INFO message from controller.");
        logger.warn("This is a WARN message from controller.");
        logger.error("This is an ERROR message from controller.");
        return "Logs generated!";
    }
}
