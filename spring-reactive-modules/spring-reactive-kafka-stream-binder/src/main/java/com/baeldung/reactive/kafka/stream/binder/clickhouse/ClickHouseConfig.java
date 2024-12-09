package com.baeldung.reactive.kafka.stream.binder.clickhouse;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClickHouseConfig {

    @Value("${clickhouse.r2dbc.url}")
    private String url;

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(url);
    }

    @Bean
    public ClickHouseRepository clickHouseRepository(ConnectionFactory connectionFactory) {
        return ClickHouseRepository.create(connectionFactory);
    }

}
