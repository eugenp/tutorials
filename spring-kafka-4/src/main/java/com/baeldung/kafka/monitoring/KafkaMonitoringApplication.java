package com.baeldung.kafka.monitoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
class KafkaMonitoringApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
            .profiles("monitoring")
            .sources(KafkaMonitoringApplication.class)
            .run(args);
    }

}