package com.baeldung.feign.clients.builder;

import com.baeldung.feign.header.authorisation.ApiAuthorisationService;
import com.baeldung.feign.header.interceptor.AuthRequestInterceptor;


import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;


public class BookFeignClientBuilder {

    public static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger(type))
            .logLevel(Logger.Level.HEADERS)
            .target(type, uri);
    }
    
    public static <T> T createClientWithInterceptor(Class<T> type, String uri) {
        return Feign.builder()
            .requestInterceptor(new AuthRequestInterceptor(new ApiAuthorisationService()))
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .logger(new Slf4jLogger(type))
            .logLevel(Logger.Level.HEADERS)
            .target(type, uri);
    }
}
