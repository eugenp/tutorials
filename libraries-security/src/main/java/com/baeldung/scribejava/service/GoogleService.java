package com.baeldung.scribejava.service;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class GoogleService {

    private OAuth20Service service;
    private final String API_KEY = "api_key";
    private final String API_SECRET = "api_secret";
    private final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    private final String CALLBACK = "http://localhost:8080/auth/google";

    @PostConstruct
    private void init(){
        this.service  = new ServiceBuilder(API_KEY)
                .apiSecret(API_SECRET)
                .scope(SCOPE)
                .callback(CALLBACK)
                .build(GoogleApi20.instance());
    }


    public OAuth20Service getService() {
        return service;
    }
}
