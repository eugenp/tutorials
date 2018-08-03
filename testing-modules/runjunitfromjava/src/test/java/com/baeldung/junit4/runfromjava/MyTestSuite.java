package com.baeldung.junit4.runfromjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ FirstUnitTest.class, SecondUnitTest.class })
public class MyTestSuite {

}
