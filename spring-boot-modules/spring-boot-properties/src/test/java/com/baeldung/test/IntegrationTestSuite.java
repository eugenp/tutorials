package com.baeldung.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.properties.basic.ExtendedPropertiesWithJavaIntegrationTest;
import com.baeldung.properties.external.ExternalPropertiesWithJavaIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ //@formatter:off
    ExternalPropertiesWithJavaIntegrationTest.class,
    ExtendedPropertiesWithJavaIntegrationTest.class,
})// @formatter:on
public final class IntegrationTestSuite {
    //
}
