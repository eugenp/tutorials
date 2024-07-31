package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("elastic")
public class CustomElasticsearchConnectionDetailsConfiguration {
    @Bean
    @Primary
    public ElasticsearchConnectionDetails getCustomElasticConnectionDetails() {
        return new CustomElasticsearchConnectionDetails();
    }
}
