package com.baeldung.kafka.serdes;

import com.baeldung.kafka.dto.MessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

// This live test needs a Docker instance running so that kafka container can be created

public class KafkaSerDesLiveTest {
    private static final String CONSUMER_APP_ID = "consumer_id";
    private static final String CONSUMER_GROUP_ID = "group_id";

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
    private final String TOPIC = "mytopic";

    private static KafkaConsumer<String, MessageDto> createKafkaConsumer() {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.baeldung.kafka.serdes.CustomDeserializer");

        return new KafkaConsumer<>(props);

    }

    private static KafkaProducer<String, MessageDto> createKafkaProducer() {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.baeldung.kafka.serdes.CustomSerializer");

        return new KafkaProducer(props);

    }

    @Before
    public void setUp() {
    }

    @Test
    public void givenKafkaClientShouldSerializeAndDeserialize() throws InterruptedException {

        MessageDto msgProd = MessageDto.builder().message("test").version("1.0").build();

        KafkaProducer<String, MessageDto> producer = createKafkaProducer();
        producer.send(new ProducerRecord<String, MessageDto>(TOPIC, "1", msgProd));
        System.out.println("Message sent " + msgProd);
        producer.close();

        Thread.sleep(2000);

        AtomicReference<MessageDto> msgCons = new AtomicReference<>();

        KafkaConsumer<String, MessageDto> consumer = createKafkaConsumer();
        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, MessageDto> records = consumer.poll(Duration.ofSeconds(1));
        records.forEach(record -> {
            msgCons.set(record.value());
            System.out.println("Message received " + record.value());
        });

        consumer.close();

        assertEquals(msgProd, msgCons.get());

    }

}


