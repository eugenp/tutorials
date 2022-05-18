package com.baeldung.feign.header.interceptor;

import com.baeldung.feign.header.authorisation.AuthorisationService;
import feign.RequestInterceptor;
import feign.RequestTemplate;


public class AuthRequestInterceptor implements RequestInterceptor {

    private AuthorisationService authTokenService;

    public AuthRequestInterceptor(AuthorisationService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorisation", authTokenService.getAuthToken());
    }
}

  