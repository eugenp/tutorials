package com.baeldung.spring.kafka.topicsandpartitions;

public class TemperatureKafkaEvent {

    private double temperature;
    private String key;

    public TemperatureKafkaEvent(double temperature, String key) {
        this.temperature = temperature;
        this.key = key;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
