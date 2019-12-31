package com.baeldung.springbootsecurity.oauth2server;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringBootAuthorizationServerApplication.class)
@ActiveProfiles("authz")
public class CustomConfigAuthorizationServerIntegrationTest extends OAuth2IntegrationTestSupport {

    URL base;

    @LocalServerPort
    int port;

    @Before
    public void setUp() throws MalformedURLException {
        base = new URL("http://localhost:" + port);
    }

    @Test
    public void givenOAuth2Context_whenAccessTokenIsRequested_ThenAccessTokenValueIsNotNull() {
        ClientCredentialsResourceDetails resourceDetails = getClientCredentialsResourceDetails("baeldung", singletonList("read"));
        OAuth2RestTemplate restTemplate = getOAuth2RestTemplate(resourceDetails);

        OAuth2AccessToken accessToken = restTemplate.getAccessToken();

        final String authentication  = restTemplate.execute(base.toString() + "/authentication", HttpMethod.GET, clientHttpRequest -> {
        }, clientHttpResponse -> IOUtils.toString(clientHttpResponse.getBody(), Charset.defaultCharset()));

        assertNotNull(accessToken);
        assertEquals(authentication, "baeldung");

    }

    @Test(expected = OAuth2AccessDeniedException.class)
    public void givenOAuth2Context_whenAccessTokenIsRequestedWithInvalidException_ThenExceptionIsThrown() {
        ClientCredentialsResourceDetails resourceDetails = getClientCredentialsResourceDetails("baeldung", singletonList("write"));
        OAuth2RestTemplate restTemplate = getOAuth2RestTemplate(resourceDetails);

        restTemplate.getAccessToken();
    }

    @Test
    public void givenOAuth2Context_whenAccessTokenIsRequestedByClientWithWriteScope_ThenAccessTokenIsNotNull() {
        ClientCredentialsResourceDetails resourceDetails = getClientCredentialsResourceDetails("baeldung-admin", singletonList("write"));
        OAuth2RestTemplate restTemplate = getOAuth2RestTemplate(resourceDetails);

        OAuth2AccessToken accessToken = restTemplate.getAccessToken();

        assertNotNull(accessToken);
    }

}

