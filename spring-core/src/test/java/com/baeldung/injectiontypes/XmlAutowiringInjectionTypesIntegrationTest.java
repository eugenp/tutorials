package com.baeldung.injectiontypes;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "classpath:injection-types-autowiring.xml")
public class XmlAutowiringInjectionTypesIntegrationTest extends AbstractInjectionTypesIntegrationTest {
}
