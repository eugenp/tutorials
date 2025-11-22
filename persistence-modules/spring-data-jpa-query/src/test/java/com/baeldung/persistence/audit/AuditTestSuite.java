package com.baeldung.persistence.audit;

// FIX: Changed to fully qualified names from the JUnit 5 platform suite API
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ 
    EnversFooBarAuditIntegrationTest.class,
    JPABarAuditIntegrationTest.class,
    SpringDataJPABarAuditIntegrationTest.class 
})
public class AuditTestSuite {
    //
}
