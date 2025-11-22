package com.baeldung.persistence;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.baeldung.persistence.audit.AuditTestSuite;

/**
 * JUnit 5 equivalent of @RunWith(Suite.class) and @Suite.SuiteClasses
 */
@Suite
@SelectClasses({ // @formatter:off
    AuditTestSuite.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}
