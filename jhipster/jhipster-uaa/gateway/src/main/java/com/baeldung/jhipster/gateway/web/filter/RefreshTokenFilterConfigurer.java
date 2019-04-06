package com.baeldung.jhipster.gateway.web.filter;

import com.baeldung.jhipster.gateway.security.oauth2.OAuth2AuthenticationService;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * Configures a RefreshTokenFilter to refresh access tokens if they are about to expire.
 *
 * @see RefreshTokenFilter
 */
public class RefreshTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    /**
     * RefreshTokenFilter needs the OAuth2AuthenticationService to refresh cookies using the refresh token.
     */
    private OAuth2AuthenticationService authenticationService;
    private final TokenStore tokenStore;

    public RefreshTokenFilterConfigurer(OAuth2AuthenticationService authenticationService, TokenStore tokenStore) {
        this.authenticationService = authenticationService;
        this.tokenStore = tokenStore;
    }

    /**
     * Install RefreshTokenFilter as a servlet Filter.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        RefreshTokenFilter customFilter = new RefreshTokenFilter(authenticationService, tokenStore);
        http.addFilterBefore(customFilter, OAuth2AuthenticationProcessingFilter.class);
    }
}
