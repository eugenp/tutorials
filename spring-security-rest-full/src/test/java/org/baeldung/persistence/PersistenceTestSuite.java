package org.baeldung.persistence;

import org.baeldung.persistence.query.JPACriteriaQueryTest;
import org.baeldung.persistence.query.JPAQuerydslTest;
import org.baeldung.persistence.query.JPASpecificationTest;
import org.baeldung.persistence.query.RsqlTest;
import org.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    // @formatter:off
    RsqlTest.class
    ,JPASpecificationTest.class
    ,FooServicePersistenceIntegrationTest.class
    ,JPAQuerydslTest.class
    ,JPACriteriaQueryTest.class
}) //
public class PersistenceTestSuite {

}
