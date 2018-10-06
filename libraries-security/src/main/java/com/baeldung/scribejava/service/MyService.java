package com.baeldung.scribejava.service;

import com.baeldung.scribejava.api.MyApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyService {

    private OAuth20Service service;
    private final String API_KEY = "baeldung_api_key";
    private final String API_SECRET = "baeldung_api_secret";

    @PostConstruct
    private void init(){
        this.service  = new ServiceBuilder(API_KEY)
                .apiSecret(API_SECRET)
                .scope("read write")
                .build(MyApi.instance());
    }


    public OAuth20Service getService() {
        return service;
    }
}
