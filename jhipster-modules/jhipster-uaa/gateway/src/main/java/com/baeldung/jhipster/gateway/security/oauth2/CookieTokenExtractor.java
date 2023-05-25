package com.baeldung.jhipster.gateway.security.oauth2;

import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Extracts the access token from a cookie.
 * Falls back to a <code>BearerTokenExtractor</code> extracting information from the Authorization header, if no
 * cookie was found.
 */
public class CookieTokenExtractor extends BearerTokenExtractor {
    /**
     * Extract the JWT access token from the request, if present.
     * If not, then it falls back to the {@link BearerTokenExtractor} behaviour.
     *
     * @param request the request containing the cookies.
     * @return the extracted JWT token; or null.
     */
    @Override
    protected String extractToken(HttpServletRequest request) {
        String result;
        Cookie accessTokenCookie = OAuth2CookieHelper.getAccessTokenCookie(request);
        if (accessTokenCookie != null) {
            result = accessTokenCookie.getValue();
        } else {
            result = super.extractToken(request);
        }
        return result;
    }

}
