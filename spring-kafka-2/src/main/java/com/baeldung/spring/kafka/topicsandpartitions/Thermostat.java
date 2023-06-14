package com.baeldung.spring.kafka.topicsandpartitions;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.Random;

public class Thermostat {

    private final KafkaTemplate<String, TemperatureKafkaEvent> kafkaTemplate;

    public Thermostat(KafkaTemplate<String, TemperatureKafkaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void measureTemperaturesAndPublish(int numMeasurements) {

        new Random().doubles(25, 35)
                .limit(numMeasurements)
                .forEach(tmp -> {
                    kafkaTemplate.send("temperature_measurements", new TemperatureKafkaEvent(tmp, ""));
                });
    }

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }
}
