package com.baeldung.before_all_global;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DatabaseSetupExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

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

    @Override
    public void close() throws Throwable {
        // Global teardown: Clean up resources
        System.out.println("Shutting down global database connections...");
        // Example: DatabaseConnectionPool.shutdown();
    }
}
