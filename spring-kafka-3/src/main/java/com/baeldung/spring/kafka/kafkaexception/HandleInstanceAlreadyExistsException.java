package com.baeldung.spring.kafka.kafkaexception;

import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.UUID;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

public class HandleInstanceAlreadyExistsException {

    public static void generateUniqueClientIDUsingUUIDRandom() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);

        String clientId = "my-producer-" + UUID.randomUUID();
        props.setProperty("client.id", clientId);
        KafkaProducer<String, String> producer1 = new KafkaProducer<>(props);

        clientId = "my-producer-" + UUID.randomUUID();
        props.setProperty("client.id", clientId);
        KafkaProducer<String, String> producer2 = new KafkaProducer<>(props);
    }

    public static void closeProducerProperlyBeforeReinstantiate() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "my-producer");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);

        KafkaProducer<String, String> producer1 = new KafkaProducer<>(props);
        producer1.close();

        producer1 = new KafkaProducer<>(props);
    }

    public static void useUniqueObjectName() throws Exception {
        MBeanServer mBeanServer1 = ManagementFactory.getPlatformMBeanServer();
        MBeanServer mBeanServer2 = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName1 = new ObjectName("kafka.server:type=KafkaMetrics,id=metric1");
        ObjectName objectName2 = new ObjectName("kafka.server:type=KafkaMetrics,id=metric2");

        MyMBean mBean1 = new MyMBean();
        mBeanServer1.registerMBean(mBean1, objectName1);

        MyMBean mBean2 = new MyMBean();
        mBeanServer2.registerMBean(mBean2, objectName2);
    }
}
