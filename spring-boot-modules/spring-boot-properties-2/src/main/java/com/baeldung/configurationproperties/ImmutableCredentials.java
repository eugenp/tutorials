package com.baeldung.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "mail.credentials")
public class ImmutableCredentials {

    private final String authMethod;
    private final String username;
    private final String password;

    @ConstructorBinding
    public ImmutableCredentials(String authMethod, String username, String password) {
        this.authMethod = authMethod;
        this.username = username;
        this.password = password;
    }

    public ImmutableCredentials(String username, String password) {
        this.username = username;
        this.password = password;
        this.authMethod = "Default";
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
