package com.baeldung.jhipster.gateway.security.oauth2;

import com.baeldung.jhipster.gateway.config.oauth2.OAuth2Properties;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Client talking to UAA's token endpoint to do different OAuth2 grants.
 */
@Component
public class UaaTokenEndpointClient extends OAuth2TokenEndpointClientAdapter implements OAuth2TokenEndpointClient {

    public UaaTokenEndpointClient(@Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
                                  JHipsterProperties jHipsterProperties, OAuth2Properties oAuth2Properties) {
        super(restTemplate, jHipsterProperties, oAuth2Properties);
    }

    @Override
    protected void addAuthentication(HttpHeaders reqHeaders, MultiValueMap<String, String> formParams) {
        reqHeaders.add("Authorization", getAuthorizationHeader());
    }

    /**
     * @return a Basic authorization header to be used to talk to UAA.
     */
    protected String getAuthorizationHeader() {
        String clientId = getClientId();
        String clientSecret = getClientSecret();
        String authorization = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
    }

}
