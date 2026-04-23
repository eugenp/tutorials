package com.baeldung.auth.server.multitenant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "multitenant-auth-server")
public class MultitenantAuthServerProperties {

    private Map<String, OAuth2AuthorizationServerProperties> tenants = new HashMap<>();

    public Map<String, OAuth2AuthorizationServerProperties> getTenants() {
        return tenants;
    }

    public void setTenants(Map<String, OAuth2AuthorizationServerProperties> tenants) {
        this.tenants = tenants;
    }
}
