package com.baeldung.customauth.authprovider;

import com.baeldung.customauth.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class RequestHeaderAuthenticationProvider implements AuthenticationProvider {

    private final AppConfig appConfig;

    @Autowired
    public RequestHeaderAuthenticationProvider(AppConfig appConfig){
        this.appConfig = appConfig;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication.getPrincipal() == null ||
                !authentication.getPrincipal().equals(appConfig.getApiAuthHeaderSecret())) {
            throw new BadCredentialsException("Incorrect principal");
        } else {
            authentication.setAuthenticated(true);
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
