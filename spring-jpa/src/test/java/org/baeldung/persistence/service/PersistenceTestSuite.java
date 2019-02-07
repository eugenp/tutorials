package org.baeldung.persistence.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({// @formatter:off
    FooPaginationPersistenceIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,FooServiceSortingTests.class
    // manual only
    // ,FooServiceSortingWitNullsManualTest.class
}) // @formatter:on
public class PersistenceTestSuite {
    //
}
