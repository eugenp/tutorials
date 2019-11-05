package com.baeldung.timer;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

class WithinWindowMatcher extends BaseMatcher<Long> {
    private final long timeout;
    private final long tolerance;

    public WithinWindowMatcher(long timeout, long tolerance) {
        this.timeout = timeout;
        this.tolerance = tolerance;
    }

    @Override
    public boolean matches(Object item) {
        final Long actual = (Long) item;
        return Math.abs(actual - timeout) < tolerance;
    }

    @Override
    public void describeTo(Description description) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Matcher<Long> withinWindow(final long timeout, final long tolerance) {
        return new WithinWindowMatcher(timeout, tolerance);
    }
}
