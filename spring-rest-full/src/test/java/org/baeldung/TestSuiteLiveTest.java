package org.baeldung;

import org.baeldung.persistence.PersistenceTestSuite;
import org.baeldung.web.LiveTestSuiteLiveTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    PersistenceTestSuite.class
    ,LiveTestSuiteLiveTest.class
}) //
public class TestSuiteLiveTest {

}
