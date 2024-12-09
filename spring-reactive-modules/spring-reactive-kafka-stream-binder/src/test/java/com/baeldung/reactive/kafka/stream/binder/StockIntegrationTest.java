package com.baeldung.reactive.kafka.stream.binder;

import com.baeldung.reactive.kafka.stream.binder.clickhouse.ClickHouseRepository;
import com.baeldung.reactive.kafka.stream.binder.consumer.StockPriceConsumer;
import com.baeldung.reactive.kafka.stream.binder.currency.CurrencyRate;
import com.baeldung.reactive.kafka.stream.binder.producer.StockPriceProducer;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.clickhouse.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootTest
@Testcontainers(parallel = true)
class StockIntegrationTest {

    @TestConfiguration
    public static class Configuration {

        @Bean
        @Primary
        public CurrencyRate currencyRate() {
            return (from, to, amount) -> Mono.just(amount * 1.2);
        }

    }

    @Container
    static ClickHouseContainer clickHouseContainer = new ClickHouseContainer("clickhouse/clickhouse-server:24.3-alpine");

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer("apache/kafka:3.9.0")
            .withEnv("KAFKA_LISTENERS", "PLAINTEXT://:9092,BROKER://:9093,CONTROLLER://:9094")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true");

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.cloud.stream.kafka.binder.brokers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("clickhouse.r2dbc.url",
                () -> "r2dbc:clickhouse://%s:%s/default".formatted(clickHouseContainer.getHost(), clickHouseContainer.getMappedPort(8123)));
    }


    @Autowired
    private StockPriceProducer producer;

    @Autowired
    private StockPriceConsumer consumer;

    @Autowired
    private ClickHouseRepository clickHouseRepository;

    @BeforeEach
    void setup() {
        clickHouseRepository.initDatabase().block();
    }

    @Test
    void contextLoads() {

        var eventCount = 200;

        producer.produceStockPrices(eventCount).subscribe();

        Awaitility
                .waitAtMost(Duration.ofSeconds(60))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() -> {
                    Assert.assertTrue(consumer.getCount() == eventCount);
                });

    }
}
