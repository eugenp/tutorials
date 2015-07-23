package org.baeldung.persistence;

import org.baeldung.persistence.hibernate.FooPaginationPersistenceIntegrationTest;
import org.baeldung.persistence.hibernate.FooSortingPersistenceServiceTest;
import org.baeldung.persistence.service.FooServiceBasicPersistenceIntegrationTest;
import org.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import org.baeldung.persistence.service.ParentServicePersistenceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    FooServiceBasicPersistenceIntegrationTest.class
    ,FooPaginationPersistenceIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,ParentServicePersistenceIntegrationTest.class
    ,FooSortingPersistenceServiceTest.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}
