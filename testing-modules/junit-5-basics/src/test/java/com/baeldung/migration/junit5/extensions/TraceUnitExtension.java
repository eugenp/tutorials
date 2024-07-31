package com.baeldung.migration.junit5.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceUnitExtension implements AfterEachCallback, BeforeEachCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceUnitExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        LOGGER.debug("Starting test ... {}", context.getDisplayName());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        LOGGER.debug("... test finished. {}", context.getDisplayName());
    }

}
