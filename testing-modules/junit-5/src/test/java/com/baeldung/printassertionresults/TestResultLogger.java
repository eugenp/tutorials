package com.baeldung.printassertionresults;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResultLogger implements TestWatcher, BeforeEachCallback {
    private static final Logger logger = LoggerFactory.getLogger(TestResultLogger.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        logger.info("Testing {}", context.getDisplayName());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("✓ {} assertion passed", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("✗ {} assertion didn't pass", context.getDisplayName());
    }
}
