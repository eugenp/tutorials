package com.baeldung.spring.boot.management.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LoggingController {
    private Log log = LogFactory.getLog(LoggingController.class);

    @GetMapping
    public String log() {
        log.trace("This is a TRACE level message");
        log.debug("This is a DEBUG level message");
        log.info("This is an INFO level message");
        log.warn("This is a WARN level message");
        log.error("This is an ERROR level message");

        return "See the log for details";
    }

    @GetMapping("/change-to-error")
    public String changeLogLevelToError() {
        LoggingSystem system = LoggingSystem.get(LoggingController.class.getClassLoader());
        system.setLogLevel(LoggingController.class.getName(), LogLevel.ERROR);

        return "changed log level to error";
    }
}
