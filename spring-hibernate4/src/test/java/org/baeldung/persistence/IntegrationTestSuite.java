package org.baeldung.persistence;

import org.baeldung.persistence.service.FooServiceBasicPersistenceIntegrationTest;
import org.baeldung.persistence.service.FooServicePaginationPersistenceIntegrationTest;
import org.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import org.baeldung.persistence.service.ParentServicePersistenceIntegrationTest;
import org.baeldung.persistence.test.FooSortingServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({// @formatter:off
    FooServiceBasicPersistenceIntegrationTest.class
    ,FooServicePaginationPersistenceIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,ParentServicePersistenceIntegrationTest.class
    ,FooSortingServiceTest.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}
