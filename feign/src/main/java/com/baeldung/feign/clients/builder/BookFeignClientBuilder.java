package com.baeldung.feign.clients.builder;

import com.baeldung.feign.header.interceptor.AuthRequestInterceptor;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;


public class BookFeignClientBuilder {

    public <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(type, uri);
    }
    
    public <T> T createClientWithInterceptor(Class<T> type, String uri) {
        return Feign.builder()
            .requestInterceptor(new AuthRequestInterceptor())
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(type, uri);
    }
    
}
