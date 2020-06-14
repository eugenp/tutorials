package com.baeldung.propertiesvsyaml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
  @PropertySource("classpath:user.properties"),
  @PropertySource("classpath:user-production.properties")
})
public class UserRoleEnvironment {

    private final Environment environment;

    @Autowired
    public UserRoleEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getUserRole() {
        return environment.getProperty("user.information.role");
    }

}
