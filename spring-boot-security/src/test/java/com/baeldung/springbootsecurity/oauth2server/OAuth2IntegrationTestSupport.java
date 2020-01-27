package com.baeldung.springbootsecurity.oauth2server;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;

public class OAuth2IntegrationTestSupport {

    public static final ResponseExtractor<String> EXTRACT_BODY_AS_STRING = clientHttpResponse ->
            IOUtils.toString(clientHttpResponse.getBody(), Charset.defaultCharset());
    private static final RequestCallback DO_NOTHING_CALLBACK = request -> {
    };

    @Value("${local.server.port}")
    protected int port;

    protected URL base;

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

    protected String executeGetRequest(OAuth2RestTemplate restTemplate, String path) {
        return restTemplate.execute(base.toString() + path, GET, DO_NOTHING_CALLBACK, EXTRACT_BODY_AS_STRING);
    }

}
