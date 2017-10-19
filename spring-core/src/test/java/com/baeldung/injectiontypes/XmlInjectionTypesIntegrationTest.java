package com.baeldung.injectiontypes;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "classpath:injection-types.xml")
public class XmlInjectionTypesIntegrationTest extends AbstractInjectionTypesIntegrationTest {
}
