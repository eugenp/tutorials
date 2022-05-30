package com.baeldung.junit5vstestng;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({ "com.baeldung.java.suite.childpackage1", "com.baeldung.java.suite.childpackage2" })
public class SelectPackagesSuiteUnitTest {

}
