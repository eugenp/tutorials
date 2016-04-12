package org.baeldung.test;

import org.baeldung.client.ClientNoSpringLiveTest;
import org.baeldung.client.ClientWithSpringLiveTest;
import org.baeldung.client.RawClientLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    RawClientLiveTest.class
    ,ClientWithSpringLiveTest.class
    ,ClientNoSpringLiveTest.class
}) //
public class LiveTestSuite {

}
