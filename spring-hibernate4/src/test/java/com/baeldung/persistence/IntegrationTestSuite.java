package com.baeldung.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.baeldung.persistence.audit.AuditTestSuite;
import com.baeldung.persistence.hibernate.FooPaginationPersistenceIntegrationTest;
import com.baeldung.persistence.hibernate.FooSortingPersistenceIntegrationTest;
import com.baeldung.persistence.service.FooServiceBasicPersistenceIntegrationTest;
import com.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import com.baeldung.persistence.service.ParentServicePersistenceIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    AuditTestSuite.class
    ,FooServiceBasicPersistenceIntegrationTest.class
    ,FooPaginationPersistenceIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,ParentServicePersistenceIntegrationTest.class
    ,FooSortingPersistenceIntegrationTest.class

}) // @formatter:on
public class IntegrationTestSuite {
    //
}
