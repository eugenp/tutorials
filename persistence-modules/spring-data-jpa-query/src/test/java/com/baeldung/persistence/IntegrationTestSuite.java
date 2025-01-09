package com.baeldung.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.baeldung.persistence.audit.AuditTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    AuditTestSuite.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}
