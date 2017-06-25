package com.baeldung.hibernate.criteria;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HibernateCriteriaTestRunner {

    public static void main(final String[] args) {
        Result result = JUnitCore.runClasses(HibernateCriteriaTestSuite.class);
        for (Failure failure : result.getFailures()) {

        }
    }
}
