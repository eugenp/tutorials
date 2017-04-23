package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DutyLogger {

    // the DutyLogger has a dependency on a Logger
    @Autowired
    private Logger logger;

    public String log() {
        return logger.log("I am a duty logger");
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}