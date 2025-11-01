package com.baeldung.kafka.sharedbroker;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

public final class EmbeddedKafkaHolder {

    private static final EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaKraftBroker(1, 1, "order", "payment").brokerListProperty(
        "spring.kafka.bootstrap-servers");

    private static volatile boolean started;

    private EmbeddedKafkaHolder() {
    }

    public static EmbeddedKafkaBroker getEmbeddedKafka() {
        if (!started) {
            synchronized (EmbeddedKafkaBroker.class) {
                if (!started) {
                    try {
                        embeddedKafka.afterPropertiesSet();
                    } catch (Exception e) {
                        throw new KafkaException("Embedded broker failed to start", e);
                    }
                    started = true;
                }
            }
        }
        return embeddedKafka;
    }
}
