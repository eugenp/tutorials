package com.baeldung.elasticsearch.importcsv;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return RestClients.create(ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build())
            .rest();
    }
}