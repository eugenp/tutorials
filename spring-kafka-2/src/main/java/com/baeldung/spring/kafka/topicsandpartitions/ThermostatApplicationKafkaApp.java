package com.baeldung.spring.kafka.topicsandpartitions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {KafkaTopicConfig.class, KafkaProducerConfig.class, KafkaConsumerConfig.class})
public class ThermostatApplicationKafkaApp implements CommandLineRunner {

    private final ThermostatService thermostatService;

    public ThermostatApplicationKafkaApp(ThermostatService thermostatService) {
        this.thermostatService = thermostatService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ThermostatApplicationKafkaApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        thermostatService.measureCelsiusAndPublish(20);
    }
}