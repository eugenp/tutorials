package com.baeldung.faunablog;

import com.faunadb.client.FaunaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
class FaunaConfiguration {
    @Value("https://db.${fauna.region}.fauna.com/")
    private String faunaUrl;

    @Value("${fauna.secret}")
    private String faunaSecret;

    @Bean
    FaunaClient getFaunaClient() throws MalformedURLException {
        return FaunaClient.builder()
                .withEndpoint(faunaUrl)
                .withSecret(faunaSecret)
                .build();
    }
}
