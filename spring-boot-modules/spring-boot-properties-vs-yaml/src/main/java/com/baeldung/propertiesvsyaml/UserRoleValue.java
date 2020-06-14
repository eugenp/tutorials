package com.baeldung.propertiesvsyaml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
  @PropertySource("classpath:user.properties"),
  @PropertySource("classpath:user-production.properties")
})
public class UserRoleValue {
    @Value("${user.information.role}")
    private String role;

    // ...
}
