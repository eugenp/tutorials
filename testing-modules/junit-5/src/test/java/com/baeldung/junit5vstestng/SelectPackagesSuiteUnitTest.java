package com.baeldung.junit5vstestng;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({ "org.baeldung.java.suite.childpackage1", "org.baeldung.java.suite.childpackage2" })
public class SelectPackagesSuiteUnitTest {

}
