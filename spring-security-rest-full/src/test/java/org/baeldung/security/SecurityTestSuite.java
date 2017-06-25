package org.baeldung.security;

import org.baeldung.security.csrf.CsrfDisabledIntegrationTest;
import org.baeldung.security.csrf.CsrfEnabledIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    // @formatter:off
    CsrfEnabledIntegrationTest.class
    ,CsrfDisabledIntegrationTest.class
}) //
public class SecurityTestSuite {

}
