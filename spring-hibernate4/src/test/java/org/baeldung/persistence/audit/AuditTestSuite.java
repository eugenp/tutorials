package org.baeldung.persistence.audit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    EnversFooBarAuditTest.class,
    JPABarAuditTest.class
}) // @formatter:on
public class AuditTestSuite {
    //
}