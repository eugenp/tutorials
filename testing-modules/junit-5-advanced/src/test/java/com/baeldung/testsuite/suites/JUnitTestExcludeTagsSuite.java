package com.baeldung.testsuite.suites;

import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.baeldung.testsuite")
@ExcludeTags("slow")
public class JUnitTestExcludeTagsSuite {
    // runs ClassOneUnitTest, ClassTwoUnitTest
}