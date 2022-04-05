package com.baeldung.monitoring;

import com.baeldung.monitoring.service.LagAnalyzerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9085", "port=9085"})
@EnableKafka
public class LiveLagAnalyzerServiceLiveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveLagAnalyzerServiceLiveTest.class);

    private static KafkaConsumer<String, String> consumer;
    private static KafkaProducer<String, String> producer;
    private static LagAnalyzerService lagAnalyzerService;
    private static final String GROUP_ID = "baeldungGrp";
    private static final String TOPIC = "baeldung";
    private static final int PARTITION = 0;
    private static final TopicPartition TOPIC_PARTITION = new TopicPartition(TOPIC, PARTITION);
    private static final String BOOTSTRAP_SERVER_CONFIG = "localhost:9085";
    private static final int BATCH_SIZE = 100;
    private static final long POLL_DURATION = 1000L;

    @Before
    public void setup() throws Exception {
        initProducer();
        initConsumer();
        lagAnalyzerService = new LagAnalyzerService(BOOTSTRAP_SERVER_CONFIG);
        consume();
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenAllProducedMessagesAreConsumed_thenLagBecomesZero()
            throws ExecutionException, InterruptedException {
        produce();
        long consumeLag = 0L;
        consume();
        Map<TopicPartition, Long> lag = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lag);
        Assert.assertEquals(1, lag.size());
        consumeLag = lag.get(TOPIC_PARTITION);
        Assert.assertEquals(0L, consumeLag);
        produce();
        produce();
        lag = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lag);
        Assert.assertEquals(1, lag.size());
        consumeLag = lag.get(TOPIC_PARTITION);
        Assert.assertEquals(200L, consumeLag);

        produce();
        produce();

        lag = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lag);
        Assert.assertEquals(1, lag.size());
        consumeLag = lag.get(TOPIC_PARTITION);
        Assert.assertEquals(400L, consumeLag);

        produce();
        lag = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lag);
        Assert.assertEquals(1, lag.size());
        consumeLag = lag.get(TOPIC_PARTITION);
        Assert.assertEquals(500L, consumeLag);

        consume();
        lag = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lag);
        Assert.assertEquals(1, lag.size());
        consumeLag = lag.get(TOPIC_PARTITION);
        Assert.assertEquals(consumeLag, 0L);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenMessageNotConsumed_thenLagIsEqualToProducedMessage()
            throws ExecutionException, InterruptedException {
        produce();
        Map<TopicPartition, Long> lagByTopicPartition = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lagByTopicPartition);
        Assert.assertEquals(1, lagByTopicPartition.size());
        long LAG = lagByTopicPartition.get(TOPIC_PARTITION);
        Assert.assertEquals(BATCH_SIZE, LAG);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenMessageConsumedLessThanProduced_thenLagIsNonZero()
            throws ExecutionException, InterruptedException {
        produce();
        consume();
        produce();
        produce();
        Map<TopicPartition, Long> lagByTopicPartition = lagAnalyzerService.analyzeLag(GROUP_ID);
        Assert.assertNotNull(lagByTopicPartition);
        Assert.assertEquals(1, lagByTopicPartition.size());
        long LAG = lagByTopicPartition.get(TOPIC_PARTITION);
        Assert.assertEquals(2 * BATCH_SIZE, LAG);
    }

    private static void consume() {
        try {
            ConsumerRecords<String, String> record = consumer.poll(Duration.ofMillis(POLL_DURATION));
            consumer.commitSync();
            Thread.sleep(POLL_DURATION);
        } catch (Exception ex) {
            LOGGER.error("Exception caught in consume", ex);
        }
    }

    private static void produce() {
        try {
            int count = BATCH_SIZE;
            while (count > 0) {
                String messageKey = UUID.randomUUID().toString();
                String messageValue = UUID.randomUUID().toString() + "_" + count;
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, messageKey, messageValue);
                RecordMetadata recordMetadata = producer.send(producerRecord).get();
                LOGGER.info("Message with key = {}, value = {} sent to partition = {}, offset = {}, topic = {}",
                        messageKey,
                        messageValue,
                        recordMetadata.partition(),
                        recordMetadata.offset(),
                        recordMetadata.topic());
                count--;
            }
        } catch (Exception ex) {
            LOGGER.error("Exception caught in produce", ex);
        }
    }

    private static void initConsumer() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_CONFIG);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        consumer = new KafkaConsumer<>(props);
        consumer.assign(Arrays.asList(TOPIC_PARTITION));
        consumer.poll(Duration.ofMillis(1L));
        consumer.commitSync();
    }

    private static void initProducer() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_CONFIG);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producer = new KafkaProducer<>(configProps);
    }
}
