package com.baeldung.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMethodNameLogger implements TestRule {

    private static final Logger LOG = LoggerFactory.getLogger(TestMethodNameLogger.class);

    @Override
    public Statement apply(Statement base, Description description) {
        logInfo("Before test", description);
        try {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    base.evaluate();
                }
            };
        } finally {
            logInfo("After test", description);
        }
    }

    private void logInfo(String msg, Description description) {
        LOG.info(msg + description.getMethodName());
    }

}
