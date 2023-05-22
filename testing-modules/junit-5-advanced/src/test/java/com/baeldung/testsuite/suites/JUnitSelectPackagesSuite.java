package com.baeldung.testsuite.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.baeldung.testsuite", "com.baeldung.testsuitetwo"})
public class JUnitSelectPackagesSuite {
    // runs ClassOneUnitTest, ClassTwoUnitTest and ClassThreeUnitTest
}