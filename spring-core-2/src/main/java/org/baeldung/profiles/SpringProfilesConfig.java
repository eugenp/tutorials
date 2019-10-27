package org.baeldung.profiles;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.baeldung.profiles")
@PropertySource(value = "classpath:application.properties")
public class SpringProfilesConfig {

}
