package org.baeldung.java.suite;

import org.baeldung.java.suite.childpackage1.Class1UnitTest;
import org.baeldung.java.suite.childpackage2.Class2UnitTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({Class1UnitTest.class, Class2UnitTest.class})
public class SelectClassesSuiteUnitTest {

}
