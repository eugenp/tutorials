package com.baeldung.junit4.runfromjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ListNodeUnitTest.class, MergeListsUnitTest.class })
public class MyTestSuite {

}
