package com.baeldung.suites;

import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.baeldung")
@ExcludePackages("com.baeldung.suites")
public class AllUnitTest {

}
 