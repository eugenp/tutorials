package com.baeldung.hexagonalarchitecture.adapters.driven;

import com.baeldung.hexagonalarchitecture.application.ports.LoggerPort;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class FileLogger implements LoggerPort {
    private static final Logger log = LoggerFactory.getLogger(FileLogger.class);

    @Override
    public void writeLog(String message) {
        log.info(message);
    }
}
