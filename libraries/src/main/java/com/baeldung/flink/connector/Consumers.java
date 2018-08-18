package com.baeldung.flink;

import com.baeldung.schema.InputMessageDeserializationSchema;
import com.baeldung.model.InputMessage;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

public class Consumers {

public static FlinkKafkaConsumer011<String> createStringConsumerForTopic(
  String topic, String kafkaAddress, String kafkaGroup ) {
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", kafkaAddress);
    props.setProperty("group.id",kafkaGroup);
    FlinkKafkaConsumer011<String> consumer =
      new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(),props);

    return consumer;
}

    public static FlinkKafkaConsumer011<InputMessage> createInputMessageConsumer(String topic, String kafkaAddress, String kafkaGroup ) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer011<InputMessage> consumer = new FlinkKafkaConsumer011<InputMessage>(
                topic, new InputMessageDeserializationSchema(),properties);

        return consumer;
    }
}
