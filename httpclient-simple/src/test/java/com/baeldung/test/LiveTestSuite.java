package com.baeldung.test;

import com.baeldung.client.ClientLiveTest;
import com.baeldung.client.RestClientLiveManualTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    RestClientLiveManualTest.class
    ,ClientLiveTest.class
}) //
public class LiveTestSuite {

}
