package com.baeldung.jmix.expensetracker.test_support;

import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * JUnit extension for providing system authentication in integration tests.
 * Should be used in {@code @ExtendWith} annotation on the test class.
 */
public class AuthenticatedAsAdmin implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        getSystemAuthenticator(context).begin("admin");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        getSystemAuthenticator(context).end();
    }

    private SystemAuthenticator getSystemAuthenticator(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        return applicationContext.getBean(SystemAuthenticator.class);
    }
}
