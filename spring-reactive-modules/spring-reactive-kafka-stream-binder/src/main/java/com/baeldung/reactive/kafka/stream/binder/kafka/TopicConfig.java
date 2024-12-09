package com.baeldung.reactive.kafka.stream.binder.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.cloud.stream.binder.kafka.BinderHeaderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.KafkaHeaderMapper;

@Configuration
public class TopicConfig {

    public static final String STOCK_PRICES_OUT = "stock-prices-out";
    public static final String STOCK_PRICES_IN = "stock-prices-in";

    @Bean(STOCK_PRICES_IN)
    public NewTopic stockPricesIn() {
        return TopicBuilder.name(STOCK_PRICES_IN)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean(STOCK_PRICES_OUT)
    public NewTopic stockPricesOut() {
        return TopicBuilder.name(STOCK_PRICES_OUT)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaHeaderMapper kafkaBinderHeaderMapper() {
        BinderHeaderMapper headerMapper = new BinderHeaderMapper();
        headerMapper.setMapAllStringsOut(true);
        return headerMapper;
    }
}
