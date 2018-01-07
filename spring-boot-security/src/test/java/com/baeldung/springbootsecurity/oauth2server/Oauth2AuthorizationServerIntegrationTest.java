package com.baeldung.springbootsecurity.oauth2server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringBootAuthorizationServerApplication.class,
                properties = { "security.oauth2.client.client-id=client", "security.oauth2.client.client-secret=secret" })
public class Oauth2AuthorizationServerIntegrationTest {

    @Value("${local.server.port}") protected int port;

    @Test
    public void whenAccessTokenIsRequested_ThenAccessTokenValueIsNotNull() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(format("http://localhost:%d/oauth/token", port));
        resourceDetails.setClientId("client");
        resourceDetails.setClientSecret("secret");
        resourceDetails.setGrantType("client_credentials");
        resourceDetails.setScope(asList("read", "write"));
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        restTemplate.setMessageConverters(singletonList(new MappingJackson2HttpMessageConverter()));
        OAuth2AccessToken accessToken = restTemplate.getAccessToken();
        assertNotNull(accessToken);

    }

}

