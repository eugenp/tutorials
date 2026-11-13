package com.baeldung.spring.kafka.shareconsumer.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ShareKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultShareConsumerFactory;
import org.springframework.kafka.core.ShareConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import com.baeldung.spring.kafka.shareconsumer.model.Event;

@EnableKafka
@Configuration
public class ShareConsumerConfig {

    private static final String SHARE_GROUP_ID = "shgroup";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${share.consumer.concurrency:1}")
    private int concurrency;

    @Bean
    public ShareConsumerFactory<String, Event> shareConsumerFactory() throws ExecutionException, InterruptedException {
        configureShareGroupOffsetReset();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.SHARE_ACQUIRE_MODE_CONFIG, "record_limit");
        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.baeldung.spring.kafka.shareconsumer.model");
        return new DefaultShareConsumerFactory<>(props);
    }

    @Bean
    public ShareKafkaListenerContainerFactory<String, Event> shareKafkaListenerContainerFactory(ShareConsumerFactory<String, Event> shareConsumerFactory) {
        ShareKafkaListenerContainerFactory<String, Event> factory = new ShareKafkaListenerContainerFactory<>(shareConsumerFactory);
        factory.getContainerProperties()
            .setShareAckMode(ContainerProperties.ShareAckMode.IMPLICIT);
        factory.setConcurrency(concurrency);
        return factory;
    }

    private void configureShareGroupOffsetReset() throws ExecutionException, InterruptedException {
        try (Admin admin = Admin.create(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers))) {
            ConfigResource shareGroup = new ConfigResource(ConfigResource.Type.GROUP, SHARE_GROUP_ID);
            AlterConfigOp earliestOffsetReset = new AlterConfigOp(new ConfigEntry("share.auto.offset.reset", "earliest"), AlterConfigOp.OpType.SET);
            admin.incrementalAlterConfigs(Map.of(shareGroup, List.of(earliestOffsetReset)))
                .all()
                .get();
        }
    }

}
