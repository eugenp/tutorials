package com.baeldung.auth0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@Configuration
public class AppConfig {
    
    @Value(value = "${com.auth0.domain}")
    private String domain;

    @Value(value = "${com.auth0.clientId}")
    private String clientId;

    @Value(value = "${com.auth0.clientSecret}")
    private String clientSecret;

    @Value(value = "${com.auth0.managementApi.clientId}")
    private String managementApiClientId;
    
    @Value(value = "${com.auth0.managementApi.clientSecret}")
    private String managementApiClientSecret;
    
    @Value(value = "${com.auth0.managementApi.grantType}")
    private String grantType;
    
    public String getDomain() {
        return domain;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    
    public String getManagementApiClientId() {
        return managementApiClientId;
    }

    public String getManagementApiClientSecret() {
        return managementApiClientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getUserInfoUrl() {
        return "https://" + getDomain() + "/userinfo";
    }
    
    public String getUsersUrl() {
        return "https://" + getDomain() + "/api/v2/users";
    }
    
    public String getUsersByEmailUrl() {
        return "https://" + getDomain() + "/api/v2/users-by-email?email=";
    }
    
    public String getLogoutUrl() {
        return "https://" + getDomain() +"/v2/logout";
    }
    
    public String getContextPath(HttpServletRequest request) {
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return path;
    }
}
