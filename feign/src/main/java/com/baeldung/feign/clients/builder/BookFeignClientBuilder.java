package com.baeldung.feign.clients.builder;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

public class BookFeignClientBuilder {

    public <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(type, uri);
    }
    
    public <T> T createClientWithInterceptor(Class<T> type, String uri, RequestInterceptor interceptor) {
        return Feign.builder()
            .requestInterceptor(interceptor)
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(type, uri);
    }
}
