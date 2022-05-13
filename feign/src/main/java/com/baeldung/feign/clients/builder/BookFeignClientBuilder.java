package com.baeldung.feign.clients.builder;

import com.baeldung.feign.header.interceptor.ApiAuthorisationService;
import com.baeldung.feign.header.interceptor.AuthRequestInterceptor;


import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;


public class BookFeignClientBuilder {

    public static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(type, uri);
    }
    
    public static <T> T createClientWithInterceptor(Class<T> type, String uri) {
        return Feign.builder()
            .requestInterceptor(new AuthRequestInterceptor(new ApiAuthorisationService()))
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(type, uri);
    }
    
}
