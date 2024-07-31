package com.baeldung.migration.junit4.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TraceUnitTestRule implements TestRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceUnitTestRule.class);

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                List<Throwable> errors = new ArrayList<Throwable>();

                LOGGER.debug("Starting test ... {}", description.getMethodName());
                try {
                    base.evaluate();
                } catch (Throwable e) {
                    errors.add(e);
                } finally {
                    LOGGER.debug("... test finished. {}", description.getMethodName());
                }

                MultipleFailureException.assertEmpty(errors);
            }
        };
    }

}
