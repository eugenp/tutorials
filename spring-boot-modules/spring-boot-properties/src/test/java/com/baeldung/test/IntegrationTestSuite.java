package com.baeldung.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baeldung.properties.basic.ExtendedPropertiesWithJavaIntegrationTest;
import com.baeldung.properties.basic.PropertiesWithMultipleXmlsIntegrationTest;
import com.baeldung.properties.basic.PropertiesWithXmlIntegrationTest;
import com.baeldung.properties.external.ExternalPropertiesWithJavaIntegrationTest;
import com.baeldung.properties.external.ExternalPropertiesWithMultipleXmlsIntegrationTest;
import com.baeldung.properties.external.ExternalPropertiesWithXmlManualTest;
import com.baeldung.properties.multiple.MultiplePropertiesXmlConfigIntegrationTest;
import com.baeldung.properties.multiple.MultiplePlaceholdersXmlConfigIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ //@formatter:off
    PropertiesWithXmlIntegrationTest.class,
    ExternalPropertiesWithJavaIntegrationTest.class,
    ExternalPropertiesWithMultipleXmlsIntegrationTest.class,
    ExternalPropertiesWithXmlManualTest.class,
    ExtendedPropertiesWithJavaIntegrationTest.class, MultiplePropertiesXmlConfigIntegrationTest.class,
    PropertiesWithMultipleXmlsIntegrationTest.class, MultiplePlaceholdersXmlConfigIntegrationTest.class
})// @formatter:on
public final class IntegrationTestSuite {
    //
}
