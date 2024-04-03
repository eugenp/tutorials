package com.baeldung.spring.kafka.kafkaexception;

import java.lang.management.ManagementFactory;
import java.util.Properties;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

@Service
public class SimulateInstanceAlreadyExistsException {
    
    public static void jmxRegistrationConflicts() throws Exception {
        // Create two instances of MBeanServer
        MBeanServer mBeanServer1 = ManagementFactory.getPlatformMBeanServer();
        MBeanServer mBeanServer2 = ManagementFactory.getPlatformMBeanServer();

        // Define the same ObjectName for both MBeans
        ObjectName objectName = new ObjectName("kafka.server:type=KafkaMetrics");

        // Create and register the first MBean
        MyMBean mBean1 = new MyMBean();
        mBeanServer1.registerMBean(mBean1, objectName);

        // Attempt to register the second MBean with the same ObjectName
        MyMBean mBean2 = new MyMBean();
        mBeanServer2.registerMBean(mBean2, objectName);
    }

    public static void duplicateConsumerClientID() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "my-consumer");
        props.put("group.id", "test-group");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", StringDeserializer.class);

        // Simulating concurrent client creation by multiple threads
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            }).start();
        }
    }

    public void duplicateProducerClientID() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "my-producer");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);

        KafkaProducer<String, String> producer1 = new KafkaProducer<>(props);
        // Attempting to create another producer using same client.id
        KafkaProducer<String, String> producer2 = new KafkaProducer<>(props);
    }

    public static void unclosedProducerAndReinitialize() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "my-producer");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);

        KafkaProducer<String, String> producer1 = new KafkaProducer<>(props);
        // Attempting to reinitialize without proper close
        producer1 = new KafkaProducer<>(props);
    }
}

class MyMBean implements DynamicMBean {

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {

    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanAttributeInfo[] attributes = new MBeanAttributeInfo[0];
        MBeanConstructorInfo[] constructors = new MBeanConstructorInfo[0];
        MBeanOperationInfo[] operations = new MBeanOperationInfo[0];
        MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[0];
        return new MBeanInfo(MyMBean.class.getName(), "My MBean", attributes, constructors, operations, notifications);
    }
}
