package com.baeldung.testsuite.suites;

import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.baeldung.testsuite")
@ExcludePackages("com.baeldung.testsuite.subpackage")
public class JUnitExcludePackagesSuite {
    // runs ClassOneUnitTest and ClassThreeUnitTest
}