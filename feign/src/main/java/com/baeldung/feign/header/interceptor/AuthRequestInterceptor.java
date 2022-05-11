package com.baeldung.feign.header.interceptor;

import java.util.UUID;

import feign.RequestInterceptor;
import feign.RequestTemplate;


public class AuthRequestInterceptor implements RequestInterceptor {
   
    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorisation", "Bearer "+AuthRequestInterceptor.getAuthorisationToken());
    }
    
    private static String getAuthorisationToken() {
        return UUID.randomUUID().toString();
    }
}

  