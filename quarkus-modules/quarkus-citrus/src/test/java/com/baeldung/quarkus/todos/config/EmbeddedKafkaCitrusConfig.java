package com.baeldung.quarkus.todos.config;

import static org.citrusframework.container.SequenceAfterSuite.Builder.afterSuite;

import org.citrusframework.container.AfterSuite;
import org.citrusframework.kafka.embedded.EmbeddedKafkaServer;
import org.citrusframework.kafka.embedded.EmbeddedKafkaServerBuilder;
import org.citrusframework.spi.BindToRegistry;

public class EmbeddedKafkaCitrusConfig {

    private EmbeddedKafkaServer kafkaServer;

    @BindToRegistry
    public EmbeddedKafkaServer kafka() {
        if (null == kafkaServer) {
            kafkaServer = new EmbeddedKafkaServerBuilder().kafkaServerPort(9092)
                .topics("todo-events")
                .build();
        }
        return kafkaServer;
    }

    @BindToRegistry
    public AfterSuite afterSuiteActions() {
        return afterSuite().actions(context -> kafka().stop())
            .build();
    }

}
