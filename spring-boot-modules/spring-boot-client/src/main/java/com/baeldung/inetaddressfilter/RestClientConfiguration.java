package com.baeldung.inetaddressfilter;

import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.boot.http.client.InetAddressFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    RestClient restClient(RestClient.Builder builder) {

        InetAddressFilter onlyExternalAddresses = InetAddressFilter.externalAddresses();
        HttpClientSettings settings = HttpClientSettings.defaults().withInetAddressFilter(onlyExternalAddresses);
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.jdk().build(settings);

        return builder
          .requestFactory(requestFactory)
          .build();
    }
}