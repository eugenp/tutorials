package com.baeldung.jhipster.gateway.security.oauth2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * A request mapper used to modify the cookies in the original request.
 * This is needed such that we can modify the cookies of the request during a token refresh.
 * The token refresh happens before authentication by the <code>OAuth2AuthenticationProcessingFilter</code>
 * so we must make sure that further in the filter chain, we have the new cookies and not the expired/missing ones.
 */
class CookiesHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * The new cookies of the request. Use these instead of the ones found in the wrapped request.
     */
    private Cookie[] cookies;

    public CookiesHttpServletRequestWrapper(HttpServletRequest request, Cookie[] cookies) {
        super(request);
        this.cookies = cookies;
    }

    /**
     * Return the modified cookies instead of the original ones.
     */
    @Override
    public Cookie[] getCookies() {
        return cookies;
    }
}
