package com.baeldung.web;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
// @formatter:off
    FooDiscoverabilityLiveTest.class,
    FooLiveTest.class,
    FooPageableLiveTest.class
}) //
public class LiveTestSuiteLiveTest {

}
