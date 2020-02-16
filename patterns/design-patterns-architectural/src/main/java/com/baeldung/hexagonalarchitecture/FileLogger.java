package com.baeldung.hexagonalarchitecture;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class FileLogger implements LoggerPort {
    private static final Logger log = LoggerFactory.getLogger(FileLogger.class);

    @Override
    public void writeLog(String message) {
        log.info(message);
    }
}
