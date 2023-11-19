package com.baeldung.junit5vstestng;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({ "com.baeldung.java.suite.junit4", "com.baeldung.java.suite.junit5" })
public class SelectPackagesSuiteUnitTest {

}
