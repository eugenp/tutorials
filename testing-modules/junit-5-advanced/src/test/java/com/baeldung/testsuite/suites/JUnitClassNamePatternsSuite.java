package com.baeldung.testsuite.suites;

import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.baeldung.testsuite")
@IncludeClassNamePatterns("com.baeldung.testsuite.Class.*UnitTest")
@ExcludeClassNamePatterns("com.baeldung.testsuite.ClassTwoUnitTest")
public class JUnitClassNamePatternsSuite {
    // runs ClassOneUnitTest and ClassThreeUnitTest
}