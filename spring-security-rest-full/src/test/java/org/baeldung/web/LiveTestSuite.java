package org.baeldung.web;

import org.baeldung.persistence.query.JPASpecificationLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    JPASpecificationLiveTest.class
    ,FooDiscoverabilityLiveTest.class
    ,FooLiveTest.class
    ,FooPageableLiveTest.class
    ,MyUserLiveTest.class
}) //
public class LiveTestSuite {

}
