package com.baeldung.cloud.netflix.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;

import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder.Default();
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("user", "ajeje");
            requestTemplate.header("password", "brazof");
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }
}
