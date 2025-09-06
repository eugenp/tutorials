package com.baeldung.before.all.global;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DatabaseSetupExtension implements BeforeAllCallback {

    private static boolean initialized = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!initialized) {
            initialized = true;
            // Global setup: Initialize database connections
            System.out.println("Initializing global database connections...");
            // Example: DatabaseConnectionPool.initialize();
        }
    }
}