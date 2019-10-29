package com.baeldung.persistence.audit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    EnversFooBarAuditIntegrationTest.class,
    JPABarAuditIntegrationTest.class,
    SpringDataJPABarAuditIntegrationTest.class
}) // @formatter:on
public class AuditTestSuite {
    //
}