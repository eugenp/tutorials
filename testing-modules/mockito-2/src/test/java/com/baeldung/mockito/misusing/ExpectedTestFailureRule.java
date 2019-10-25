package com.baeldung.mockito.misusing;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class ExpectedTestFailureRule implements MethodRule {

    private final MethodRule testedRule;
    private FailureAssert failureAssert = null;

    public ExpectedTestFailureRule(MethodRule testedRule) {
        this.testedRule = testedRule;
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            public void evaluate() throws Throwable {
                try {
                    testedRule.apply(base, method, target)
                        .evaluate();
                } catch (Throwable t) {
                    if (failureAssert == null) {
                        throw t;
                    }
                    failureAssert.doAssert(t);
                    return;
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    public void expectedFailure(final Class<? extends Throwable> expected) {
        FailureAssert assertion = t -> Assert.assertThat(t, Matchers.isA((Class<Throwable>) expected));
        this.expectedFailure(assertion);
    }

    private void expectedFailure(FailureAssert failureAssert) {
        this.failureAssert = failureAssert;
    }

    @FunctionalInterface
    private interface FailureAssert {
        abstract void doAssert(Throwable t);
    }

}
