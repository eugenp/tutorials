package com.baeldung.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate(
            @Value("${pcf.v1.connectionRequestTimeout}") final int connectionRequestTimeout,
            @Value("${pcf.v1.connectionTimeout}") final int connectionTimeout,
            @Value("${pcf.v1.readTimeout}") final int readTimeout) {

        return new RestTemplate(new BufferingClientHttpRequestFactory(clientHttpRequestFactory(
                        connectionRequestTimeout, connectionTimeout, readTimeout)));
    }

    //Used to define Timeout configuration
    private ClientHttpRequestFactory clientHttpRequestFactory(
            final int connectionRequestTimeout,
            final int connectionTimeout,
            final int readTimeout) {

        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(connectionRequestTimeout);
        factory.setReadTimeout(connectionTimeout);
        factory.setConnectTimeout(readTimeout);
        return factory;
    }

}
