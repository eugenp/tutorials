package com.baeldung.testsuite.suites;

import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.baeldung.testsuite")
@IncludePackages("com.baeldung.testsuite.subpackage")
public class JUnitTestIncludePackagesSuite {
    // runs ClassTwoUnitTest
}