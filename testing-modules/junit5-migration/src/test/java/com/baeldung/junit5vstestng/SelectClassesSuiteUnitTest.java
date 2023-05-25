package com.baeldung.junit5vstestng;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({Class1UnitTest.class, Class2UnitTest.class})
public class SelectClassesSuiteUnitTest {

}
