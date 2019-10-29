package com.baeldung.springbootsecurity.oauth2server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

public class OAuth2IntegrationTestSupport {

    @Value("${local.server.port}") protected int port;

    protected ClientCredentialsResourceDetails getClientCredentialsResourceDetails(final String clientId, final List<String> scopes) {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(format("http://localhost:%d/oauth/token", port));
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret("baeldung");
        resourceDetails.setScope(scopes);
        resourceDetails.setGrantType("client_credentials");
        return resourceDetails;
    }

    protected OAuth2RestTemplate getOAuth2RestTemplate(final ClientCredentialsResourceDetails resourceDetails) {
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        restTemplate.setMessageConverters(singletonList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }
}
