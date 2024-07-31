package com.baeldung.persistence.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    FooPaginationPersistenceIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,FooServiceSortingIntegrationTest.class
    ,FooServiceSortingWitNullsManualIntegrationTest.class
}) // @formatter:on
public class PersistenceTestSuite {
    //
}
