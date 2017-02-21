package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskLogger {

    // the TaskLogger has a dependency on a Logger
    private Logger logger;

    // Spring container injects a Logger through the constructor
    @Autowired
    public TaskLogger(Logger logger) {
        this.logger = logger;
    }

    public String log() {
        return logger.log("I am a task logger");
    }
}
