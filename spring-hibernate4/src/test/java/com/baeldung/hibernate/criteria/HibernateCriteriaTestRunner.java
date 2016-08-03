package com.baeldung.hibernate.criteria;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HibernateCriteriaTestRunner {

    public static void main(final String[] args) {

	final Result result = JUnitCore.runClasses(HibernateCriteriaTestSuite.class);
	for (final Failure failure : result.getFailures()) {
	}

    }
}

