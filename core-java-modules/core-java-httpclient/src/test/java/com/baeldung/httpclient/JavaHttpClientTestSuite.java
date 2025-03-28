package com.baeldung.httpclient;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Log-analyzing testing necessitates sequential execution.
 */
@Suite
@SelectClasses({HttpClientConnectionManagementUnitTest.class
        , HttpClientPostUnitTest.class, JavaHttpClientExceptionHandlingUnitTest.class})
public class JavaHttpClientTestSuite {
}
