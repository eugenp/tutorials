package com.baeldung.port;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.baeldung.port.audit.AuditTestSuite;
import com.baeldung.port.hibernate.FooPaginationPersistenceIntegrationTest;
import com.baeldung.port.hibernate.FooSortingPersistenceIntegrationTest;
import com.baeldung.port.service.FooServiceBasicPersistenceIntegrationTest;
import com.baeldung.port.service.FooServicePersistenceIntegrationTest;
import com.baeldung.port.service.ParentServicePersistenceIntegrationTest;

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
