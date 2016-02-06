package com.baeldung.persistence;

import com.baeldung.persistence.hibernate.FooPaginationPersistenceIntegrationTest;
import com.baeldung.persistence.hibernate.FooSortingPersistenceServiceTest;
import com.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import com.baeldung.persistence.service.FooServiceBasicPersistenceIntegrationTest;
import com.baeldung.persistence.service.ParentServicePersistenceIntegrationTest;
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
