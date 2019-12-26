package com.baeldung.jhipster.gateway.security.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Client talking to an OAuth2 Authorization server token endpoint.
 *
 * @see UaaTokenEndpointClient
 * @see OAuth2TokenEndpointClientAdapter
 */
public interface OAuth2TokenEndpointClient {
    /**
     * Send a password grant to the token endpoint.
     *
     * @param username the username to authenticate.
     * @param password his password.
     * @return the access token and enclosed refresh token received from the token endpoint.
     * @throws org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException
     * if we cannot contact the token endpoint.
     */
    OAuth2AccessToken sendPasswordGrant(String username, String password);

    /**
     * Send a refresh_token grant to the token endpoint.
     *
     * @param refreshTokenValue the refresh token used to get new tokens.
     * @return the new access/refresh token pair.
     * @throws org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException
     * if we cannot contact the token endpoint.
     */
    OAuth2AccessToken sendRefreshGrant(String refreshTokenValue);
}
