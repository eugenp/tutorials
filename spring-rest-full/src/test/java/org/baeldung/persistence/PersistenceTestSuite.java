package org.baeldung.persistence;

import org.baeldung.persistence.service.FooServicePersistenceIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    // @formatter:off

    FooServicePersistenceIntegrationTest.class

}) //
public class PersistenceTestSuite {

}
