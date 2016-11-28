package org.baeldung.persistence;

import org.baeldung.persistence.query.JPACriteriaQueryIntegrationTest;
import org.baeldung.persistence.query.JPAQuerydslIntegrationTest;
import org.baeldung.persistence.query.JPASpecificationIntegrationTest;
import org.baeldung.persistence.query.RsqlIntegrationTest;
import org.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    // @formatter:off
    RsqlIntegrationTest.class
    ,JPASpecificationIntegrationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,JPAQuerydslIntegrationTest.class
    ,JPACriteriaQueryIntegrationTest.class
}) //
public class PersistenceTestSuite {

}
