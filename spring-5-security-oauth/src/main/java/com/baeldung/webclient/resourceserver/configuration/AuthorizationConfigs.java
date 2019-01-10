package com.baeldung.webclient.resourceserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
public class AuthorizationConfigs {

    @Value("${oauth.authserver.client-id}")
    String clientId;

    @Value("${oauth.authserver.client-secret}")
    String clientSecret;
    
    @Value("${oauth.authserver.check-token-endpoint}")
    String checkTokenEndpoint;

    @Bean
    public ResourceServerTokenServices tokenSvc() {
        RemoteTokenServices remoteService = new RemoteTokenServices();
        remoteService.setCheckTokenEndpointUrl(checkTokenEndpoint);
        remoteService.setClientId(clientId);
        remoteService.setClientSecret(clientSecret);
        return remoteService;
    }
}
