package com.baeldung.junit4.runfromjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ListNodeTest.class, MergeListsTest.class })
public class MyTestSuite {

}
