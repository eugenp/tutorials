package com.baeldung.testsuite.suites;

import com.baeldung.testsuite.ClassOneUnitTest;
import org.junit.platform.suite.api.SelectMethod;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("My Test Suite")
@SelectMethod(type = ClassOneUnitTest.class, name = "whenFalse_thenFalse")
@SelectMethod("com.baeldung.testsuite.subpackage.ClassTwoUnitTest#whenFalse_thenFalse")
public class JUnitSelectMethodsSuite {
    // runs ClassOneUnitTest and ClassTwoUnitTest
}