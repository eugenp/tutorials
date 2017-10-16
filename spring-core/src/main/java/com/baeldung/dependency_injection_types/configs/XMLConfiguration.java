package com.baeldung.dependency_injection_types.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath:constructor-dependency-injection.xml", "classpath:setter-dependency-injection.xml" })
public class XMLConfiguration {
}
