package com.baeldung.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpClientConfig {

    // if using WebClient
    @Bean
    public MyHttpClient webClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(WebClient.builder()
          .baseUrl("http://localhost:8080").build()))
            .build();
        return factory.createClient(MyHttpClient.class);
    }

    // if using RestTemplate
    @Bean
    public MyHttpClient restTemplateClient() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
          .build();
        return factory.createClient(MyHttpClient.class);
    }
}