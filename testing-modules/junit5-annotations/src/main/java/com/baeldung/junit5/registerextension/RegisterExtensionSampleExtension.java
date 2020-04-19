package com.baeldung.junit5.registerextension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This extension is meant to demonstrate the use of RegisterExtension.
 */
public class RegisterExtensionSampleExtension implements BeforeAllCallback, BeforeEachCallback {

    private final String type;
    Logger logger = LoggerFactory.getLogger(RegisterExtensionSampleExtension.class);

    public RegisterExtensionSampleExtension(String type) {
        this.type = type;
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        logger.info("Type {} In beforeAll : {}", type, extensionContext.getDisplayName());
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        logger.info("Type {} In beforeEach : {}", type, extensionContext.getDisplayName());
    }

    public String getType() {
        return type;
    }
}
