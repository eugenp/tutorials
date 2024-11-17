package com.baeldung.kafka.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ImperativeElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo("elasticsearch:9200")
            .usingSsl(false)
            .withConnectTimeout(Duration.ofSeconds(30))
            .withSocketTimeout(Duration.ofSeconds(30))
            .withBasicAuth("elastic", "Elasticsearch@123")
            .withHeaders(() -> {
                HttpHeaders headers = new HttpHeaders();
                headers.add("currentTime", LocalDateTime.now()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                return headers;
            })
            .build();
        return clientConfiguration;
    }
}