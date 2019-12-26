package com.baeldung.scribejava.service;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TwitterService {

    private final String API_KEY = "api_key";
    private final String API_SECRET = "api_secret";
    private OAuth10aService service;

    @PostConstruct
    private void init(){
        this.service = new ServiceBuilder(API_KEY)
                .apiSecret(API_SECRET)
                .build(TwitterApi.instance());
    }

    public OAuth10aService getService(){
        return service;
    }


}
