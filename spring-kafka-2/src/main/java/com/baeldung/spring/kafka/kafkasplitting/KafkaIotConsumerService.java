package com.baeldung.spring.kafka.kafkasplitting;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaIotConsumerService {

    @KafkaListener(topics = "iot_sensor_data")
    public void consumeIotData(IotSensorData item) {
        System.out.println("Consumed Message in original \"iot_sensor_data\" topic :" + item.getSensorType());
    }

    @KafkaListener(topics = "iot_sensor_data_temp")
    public void consumeIotTemperatureData(IotSensorData item) {
        System.out.println("Consumed Temparature data :" + item.getValue());
    }

    @KafkaListener(topics = "iot_sensor_data_hum")
    public void consumeIotHumidityData(IotSensorData item) {
        System.out.println("Consumed Humidity data :" + item.getValue());
    }

    @KafkaListener(topics = "iot_sensor_data_move")
    public void consumeIotMovementData(IotSensorData item) {
        System.out.println("Consumed Movement data :" + item.getValue());
    }
}