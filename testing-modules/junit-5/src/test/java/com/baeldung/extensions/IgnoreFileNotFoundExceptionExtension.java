package com.baeldung.extensions;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class IgnoreFileNotFoundExceptionExtension implements TestExecutionExceptionHandler {

    Logger logger = LogManager.getLogger(IgnoreFileNotFoundExceptionExtension.class);

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        if (throwable instanceof FileNotFoundException) {
            logger.error("File not found:" + throwable.getMessage());
            return;
        }
        throw throwable;
    }
}
