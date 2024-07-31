package com.baeldung.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageLogger implements TestRule {

    private static final Logger LOG = LoggerFactory.getLogger(MessageLogger.class);

    private String message;

    public MessageLogger(String message) {
        this.message = message;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    LOG.info("Starting: {}", message);
                    base.evaluate();
                } finally {
                    LOG.info("Finished: {}", message);
                }
            }
        };
    }

}
