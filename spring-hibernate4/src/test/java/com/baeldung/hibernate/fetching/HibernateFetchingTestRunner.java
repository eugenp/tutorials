package com.baeldung.hibernate.fetching;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HibernateFetchingTestRunner {

	public static void main(final String[] args) {

		Result result = JUnitCore.runClasses(HibernateFetchingTestSuite.class);
		for (Failure failure : result.getFailures()) {

		}

	}
}
