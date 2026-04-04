package com.baeldung.spring.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public ChristmasJoyClient christmasJoyClient(
      @Value("${application.rest.services.christmasJoy.baseUrl}") String baseUrl
    ) {
        RestClient restClient = RestClient.builder()
          .baseUrl(baseUrl)
          .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
          .builderFor(RestClientAdapter.create(restClient))
          .build();

        return factory.createClient(ChristmasJoyClient.class);
    }

}