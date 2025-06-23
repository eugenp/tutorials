package com.baeldung.retryjunit;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class RetryExtension implements TestExecutionExceptionHandler {
    private static final int MAX_RETRIES = 3;
    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create("RetryExtension");

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {
        Store store = context.getStore(NAMESPACE);
        int retries = store.getOrDefault("retries", Integer.class, 0);

        if (retries < MAX_RETRIES) {
            retries++;
            store.put("retries", retries);
            System.out.println("Retrying test " + context.getDisplayName() + ", attempt " + retries);
            throw throwable;
        } else {
            throw throwable;
        }
    }
}
