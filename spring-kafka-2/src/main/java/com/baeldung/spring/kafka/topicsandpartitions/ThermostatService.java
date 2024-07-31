package com.baeldung.spring.kafka.topicsandpartitions;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ThermostatService {

    private final KafkaTemplate<String, Double> kafkaTemplate;

    public ThermostatService(KafkaTemplate<String, Double> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void measureCelsiusAndPublish(int numMeasurements) {
        new Random().doubles(25, 35)
                .limit(numMeasurements)
                .forEach(tmp -> {
                    kafkaTemplate.send("celcius-scale-topic", tmp);
                });
    }
}
