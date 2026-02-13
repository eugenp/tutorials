package com.baeldung.tokenexchange.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.TokenExchangeOAuth2AuthorizedClientProvider;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UserServiceApplication.class);
        application.setAdditionalProfiles("userservice");
        application.run(args);
    }

    @Bean
    public RestClient messageServiceRestClient() {
        return RestClient.builder()
            .baseUrl("http://localhost:8082")
            .build();
    }

    @Bean
    public OAuth2AuthorizedClientProvider tokenExchange() {
        return new TokenExchangeOAuth2AuthorizedClientProvider();
    }

}
