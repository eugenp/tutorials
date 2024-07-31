package com.baeldung.testsuite.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.baeldung.testsuite")
@IncludeTags("slow")
public class JUnitTestIncludeTagsSuite {
    // runs ClassTwoUnitTest
}