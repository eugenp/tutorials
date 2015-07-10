package org.baeldung.properties.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:child.properties")
public class ChildConfig {

}
