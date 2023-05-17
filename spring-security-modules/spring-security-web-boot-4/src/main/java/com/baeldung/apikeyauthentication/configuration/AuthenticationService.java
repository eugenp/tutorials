package com.baeldung.apikeyauthentication.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String AUTH_TOKEN = "Baeldung";

    public static Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey != null && apiKey.equals(AUTH_TOKEN)) {
            return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
        }

        return null;
    }
}
