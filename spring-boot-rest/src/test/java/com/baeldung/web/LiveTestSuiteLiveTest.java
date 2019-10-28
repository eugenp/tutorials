package com.baeldung.web;

import com.baeldung.web.FooDiscoverabilityLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    FooDiscoverabilityLiveTest.class,
    FooLiveTest.class,
    FooPageableLiveTest.class
}) //
public class LiveTestSuiteLiveTest {

}
