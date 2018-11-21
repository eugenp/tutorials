package com.baeldung.jhipster.gateway.web.filter;

import com.baeldung.jhipster.gateway.security.oauth2.OAuth2AuthenticationService;
import com.baeldung.jhipster.gateway.security.oauth2.OAuth2CookieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters incoming requests and refreshes the access token before it expires.
 */
public class RefreshTokenFilter extends GenericFilterBean {
    /**
     * Number of seconds before expiry to start refreshing access tokens so we don't run into race conditions when forwarding
     * requests downstream. Otherwise, access tokens may still be valid when we check here but may then be expired
     * when relayed to another microservice a wee bit later.
     */
    private static final int REFRESH_WINDOW_SECS = 30;

    private final Logger log = LoggerFactory.getLogger(RefreshTokenFilter.class);

    /**
     * The OAuth2AuthenticationService is doing the actual work. We are just a simple filter after all.
     */
    private final OAuth2AuthenticationService authenticationService;
    private final TokenStore tokenStore;

    public RefreshTokenFilter(OAuth2AuthenticationService authenticationService, TokenStore tokenStore) {
        this.authenticationService = authenticationService;
        this.tokenStore = tokenStore;
    }

    /**
     * Check access token cookie and refresh it, if it is either not present, expired or about to expire.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        try {
            httpServletRequest = refreshTokensIfExpiring(httpServletRequest, httpServletResponse);
        } catch (ClientAuthenticationException ex) {
            log.warn("Security exception: could not refresh tokens", ex);
            httpServletRequest = authenticationService.stripTokens(httpServletRequest);
        }
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    /**
     * Refresh the access and refresh tokens if they are about to expire.
     *
     * @param httpServletRequest  the servlet request holding the current cookies. If no refresh cookie is present,
     *                            then we are out of luck.
     * @param httpServletResponse the servlet response that gets the new set-cookie headers, if they had to be
     *                            refreshed.
     * @return a new request to use downstream that contains the new cookies, if they had to be refreshed.
     * @throws InvalidTokenException if the tokens could not be refreshed.
     */
    public HttpServletRequest refreshTokensIfExpiring(HttpServletRequest httpServletRequest, HttpServletResponse
        httpServletResponse) {
        HttpServletRequest newHttpServletRequest = httpServletRequest;
        //get access token from cookie
        Cookie accessTokenCookie = OAuth2CookieHelper.getAccessTokenCookie(httpServletRequest);
        if (mustRefreshToken(accessTokenCookie)) {        //we either have no access token, or it is expired, or it is about to expire
            //get the refresh token cookie and, if present, request new tokens
            Cookie refreshCookie = OAuth2CookieHelper.getRefreshTokenCookie(httpServletRequest);
            if (refreshCookie != null) {
                try {
                    newHttpServletRequest = authenticationService.refreshToken(httpServletRequest, httpServletResponse, refreshCookie);
                } catch (HttpClientErrorException ex) {
                    throw new UnauthorizedClientException("could not refresh OAuth2 token", ex);
                }
            } else if (accessTokenCookie != null) {
                log.warn("access token found, but no refresh token, stripping them all");
                OAuth2AccessToken token = tokenStore.readAccessToken(accessTokenCookie.getValue());
                if (token.isExpired()) {
                    throw new InvalidTokenException("access token has expired, but there's no refresh token");
                }
            }
        }
        return newHttpServletRequest;
    }

    /**
     * Check if we must refresh the access token.
     * We must refresh it, if we either have no access token, or it is expired, or it is about to expire.
     *
     * @param accessTokenCookie the current access token.
     * @return true, if it must be refreshed; false, otherwise.
     */
    private boolean mustRefreshToken(Cookie accessTokenCookie) {
        if (accessTokenCookie == null) {
            return true;
        }
        OAuth2AccessToken token = tokenStore.readAccessToken(accessTokenCookie.getValue());
        //check if token is expired or about to expire
        if (token.isExpired() || token.getExpiresIn() < REFRESH_WINDOW_SECS) {
            return true;
        }
        return false;       //access token is still fine
    }
}
