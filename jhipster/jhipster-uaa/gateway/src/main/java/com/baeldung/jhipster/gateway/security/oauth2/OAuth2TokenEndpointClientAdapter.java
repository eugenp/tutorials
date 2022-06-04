package com.baeldung.jhipster.gateway.security.oauth2;

import com.baeldung.jhipster.gateway.config.oauth2.OAuth2Properties;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Default base class for an OAuth2TokenEndpointClient.
 * Individual implementations for a particular OAuth2 provider can use this as a starting point.
 */
public abstract class OAuth2TokenEndpointClientAdapter implements OAuth2TokenEndpointClient {
    private final Logger log = LoggerFactory.getLogger(OAuth2TokenEndpointClientAdapter.class);
    protected final RestTemplate restTemplate;
    protected final JHipsterProperties jHipsterProperties;
    protected final OAuth2Properties oAuth2Properties;

    public OAuth2TokenEndpointClientAdapter(RestTemplate restTemplate, JHipsterProperties jHipsterProperties, OAuth2Properties oAuth2Properties) {
        this.restTemplate = restTemplate;
        this.jHipsterProperties = jHipsterProperties;
        this.oAuth2Properties = oAuth2Properties;
    }

    /**
     * Sends a password grant to the token endpoint.
     *
     * @param username the username to authenticate.
     * @param password his password.
     * @return the access token.
     */
    @Override
    public OAuth2AccessToken sendPasswordGrant(String username, String password) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.set("username", username);
        formParams.set("password", password);
        formParams.set("grant_type", "password");
        addAuthentication(reqHeaders, formParams);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, reqHeaders);
        log.debug("contacting OAuth2 token endpoint to login user: {}", username);
        ResponseEntity<OAuth2AccessToken>
            responseEntity = restTemplate.postForEntity(getTokenEndpoint(), entity, OAuth2AccessToken.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.debug("failed to authenticate user with OAuth2 token endpoint, status: {}", responseEntity.getStatusCodeValue());
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }
        OAuth2AccessToken accessToken = responseEntity.getBody();
        return accessToken;
    }

    /**
     * Sends a refresh grant to the token endpoint using the current refresh token to obtain new tokens.
     *
     * @param refreshTokenValue the refresh token to use to obtain new tokens.
     * @return the new, refreshed access token.
     */
    @Override
    public OAuth2AccessToken sendRefreshGrant(String refreshTokenValue) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshTokenValue);
        HttpHeaders headers = new HttpHeaders();
        addAuthentication(headers, params);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        log.debug("contacting OAuth2 token endpoint to refresh OAuth2 JWT tokens");
        ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.postForEntity(getTokenEndpoint(), entity,
                                                                                      OAuth2AccessToken.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.debug("failed to refresh tokens: {}", responseEntity.getStatusCodeValue());
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }
        OAuth2AccessToken accessToken = responseEntity.getBody();
        log.info("refreshed OAuth2 JWT cookies using refresh_token grant");
        return accessToken;
    }

    protected abstract void addAuthentication(HttpHeaders reqHeaders, MultiValueMap<String, String> formParams);

    protected String getClientSecret() {
        String clientSecret = oAuth2Properties.getWebClientConfiguration().getSecret();
        if (clientSecret == null) {
            throw new InvalidClientException("no client-secret configured in application properties");
        }
        return clientSecret;
    }

    protected String getClientId() {
        String clientId = oAuth2Properties.getWebClientConfiguration().getClientId();
        if (clientId == null) {
            throw new InvalidClientException("no client-id configured in application properties");
        }
        return clientId;
    }

    /**
     * Returns the configured OAuth2 token endpoint URI.
     *
     * @return the OAuth2 token endpoint URI.
     */
    protected String getTokenEndpoint() {
        String tokenEndpointUrl = jHipsterProperties.getSecurity().getClientAuthorization().getAccessTokenUri();
        if (tokenEndpointUrl == null) {
            throw new InvalidClientException("no token endpoint configured in application properties");
        }
        return tokenEndpointUrl;
    }

}
