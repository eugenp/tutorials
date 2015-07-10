package org.baeldung.properties.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:parent.properties")
public class ParentConfig {

}
