package org.baeldung.test;

import org.baeldung.properties.core.ExternalPropertiesWithJavaIntegrationTest;
import org.baeldung.properties.core.ExternalPropertiesWithMultipleXmlsIntegrationTest;
import org.baeldung.properties.core.ExternalPropertiesWithXmlIntegrationTest;
import org.baeldung.properties.core.PropertiesWithJavaIntegrationTest;
import org.baeldung.properties.core.PropertiesWithMultipleXmlsIntegrationTest;
import org.baeldung.properties.core.PropertiesWithXmlIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //@formatter:off
    PropertiesWithXmlIntegrationTest.class,
    ExternalPropertiesWithJavaIntegrationTest.class,
    ExternalPropertiesWithMultipleXmlsIntegrationTest.class,
    ExternalPropertiesWithXmlIntegrationTest.class,
    PropertiesWithJavaIntegrationTest.class,
    PropertiesWithMultipleXmlsIntegrationTest.class,
})// @formatter:on
public final class IntegrationTestSuite {
    //
}
