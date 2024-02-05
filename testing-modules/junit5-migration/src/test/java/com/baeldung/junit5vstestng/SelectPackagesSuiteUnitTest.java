package com.baeldung.junit5vstestng;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({ "com.baeldung.junit4", "com.baeldung.junit5" })
class SelectPackagesSuiteUnitTest {

}
