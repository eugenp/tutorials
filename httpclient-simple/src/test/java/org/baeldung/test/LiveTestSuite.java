package org.baeldung.test;

import org.baeldung.client.ClientLiveTest;
import org.baeldung.client.RestClientLiveManualTest;
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
