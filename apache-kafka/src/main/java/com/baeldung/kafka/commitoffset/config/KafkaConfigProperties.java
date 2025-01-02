package com.baeldung.kafka.commitoffset.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author amitkumar
 */
public class KafkaConfigProperties {
    public static final String MY_TOPIC = "my-topic";

    public static Properties getProperties() {

        Properties props = new Properties();
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "MyFirstConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return props;
    }

    public static ArrayList<String> getTopic() {
        ArrayList<String> topics = new ArrayList<>();
        topics.add(MY_TOPIC);
        return topics;
    }
}
