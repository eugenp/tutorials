package com.baeldung.junit4;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

public class TraceUnitTestRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                List<Throwable> errors = new ArrayList<Throwable>();

                System.out.println("Starting test ... " + description.getMethodName());
                try {
                    base.evaluate();
                } catch (Throwable e) {
                    errors.add(e);
                } finally {
                    System.out.println("... test finished. " + description.getMethodName());
                }

                MultipleFailureException.assertEmpty(errors);
            }
        };
    }

}
