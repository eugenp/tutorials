package com.baeldung.migration.junit5.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.TestExtensionContext;

public class TraceUnitExtension implements AfterEachCallback, BeforeEachCallback {

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        System.out.println("Starting test ... " + context.getDisplayName());
    }

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        System.out.println("... test finished. " + context.getDisplayName());
    }

}
