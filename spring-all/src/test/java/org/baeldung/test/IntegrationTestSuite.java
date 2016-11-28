package org.baeldung.test;

import org.baeldung.properties.basic.ExtendedPropertiesWithJavaIntegrationTest;
import org.baeldung.properties.basic.PropertiesWithMultipleXmlsIntegrationTest;
import org.baeldung.properties.basic.PropertiesWithXmlIntegrationTest;
import org.baeldung.properties.external.ExternalPropertiesWithJavaIntegrationTest;
import org.baeldung.properties.external.ExternalPropertiesWithMultipleXmlsIntegrationTest;
import org.baeldung.properties.external.ExternalPropertiesWithXmlIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //@formatter:off
    PropertiesWithXmlIntegrationTest.class,
    ExternalPropertiesWithJavaIntegrationTest.class,
    ExternalPropertiesWithMultipleXmlsIntegrationTest.class,
    ExternalPropertiesWithXmlIntegrationTest.class,
    ExtendedPropertiesWithJavaIntegrationTest.class,
    PropertiesWithMultipleXmlsIntegrationTest.class,
})// @formatter:on
public final class IntegrationTestSuite {
    //
}
