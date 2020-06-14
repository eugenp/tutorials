package com.baeldung.propertiesvsyaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user.information")
@PropertySources({
  @PropertySource("classpath:user.properties"),
  @PropertySource("classpath:user-production.properties")
})
@Configuration("userProperties")
public class UserConfigurationProperties {
    private String role;
    private String name;
    private String password;
}

