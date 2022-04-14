package com.baeldung.openid.oidc.jwtauthorities.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "baeldung.jwt.mapping")
public class JwtMappingProperties {
    
    private String authoritiesPrefix;
    private String authoritiesClaimName;
    private String principalClaimName;
    private Map<String,String> scopes;

    public String getAuthoritiesClaimName() {
        return authoritiesClaimName;
    }

    public void setAuthoritiesClaimName(String authoritiesClaimName) {
        this.authoritiesClaimName = authoritiesClaimName;
    }

    public String getAuthoritiesPrefix() {
        return authoritiesPrefix;
    }

    public void setAuthoritiesPrefix(String authoritiesPrefix) {
        this.authoritiesPrefix = authoritiesPrefix;
    }
    
    
    public String getPrincipalClaimName() {
        return principalClaimName;
    }

    public void setPrincipalClaimName(String principalClaimName) {
        this.principalClaimName = principalClaimName;
    }

    public Map<String,String> getScopes() {
        return scopes;
    }

    public void setScopes(Map<String,String> scopes) {
        this.scopes = scopes;
    }
    

}
