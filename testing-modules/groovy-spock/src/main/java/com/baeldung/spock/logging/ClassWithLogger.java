package com.baeldung.spock.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassWithLogger {

    void logInfo() {
        log.info("info message");
    }

    void logInfoWithParameter(final String extraData) {
        log.info("info message: {}", extraData);
    }

}
