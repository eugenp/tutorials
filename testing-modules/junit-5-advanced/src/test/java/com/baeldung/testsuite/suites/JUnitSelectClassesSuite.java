package com.baeldung.testsuite.suites;

import com.baeldung.testsuite.ClassOneUnitTest;
import com.baeldung.testsuite.subpackage.ClassTwoUnitTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("My Test Suite")
@SelectClasses({ClassOneUnitTest.class, ClassTwoUnitTest.class})
public class JUnitSelectClassesSuite {
    // runs ClassOneUnitTest and ClassTwoUnitTest
}