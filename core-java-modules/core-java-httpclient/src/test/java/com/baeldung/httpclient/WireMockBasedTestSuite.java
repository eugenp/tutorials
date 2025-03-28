package com.baeldung.httpclient;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Log-analyzing testing necessitates sequential execution of tests involving WireMock.
 */
@Suite
@SelectClasses({HttpClientConnectionManagementUnitTest.class
        , JavaHttpClientExceptionHandlingUnitTest.class})
public class WireMockBasedTestSuite {
}
