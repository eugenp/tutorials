package com.baeldung.monitoring.simulation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import io.micrometer.core.instrument.MeterRegistry;

@EnableKafka
@Component
public class KafkaConsumerConfig {

    @Value(value = "${monitor.kafka.bootstrap.config}")
    private String bootstrapAddress;
    @Value(value = "${monitor.kafka.consumer.groupid}")
    private String groupId;

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 0);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(props);
        consumerFactory.addListener(new MicrometerConsumerListener<>(this.meterRegistry));
        return consumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
      kafkaListenerContainerFactory(@Qualifier("consumerFactory") ConsumerFactory<String,
      String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory =
          new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(consumerFactory);
        return listenerContainerFactory;
    }
}
