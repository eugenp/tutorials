package com.baeldung.kafka.producer;

import com.baeldung.kafka.producer.EvenOddPartitioner;
import com.baeldung.kafka.producer.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KafkaProducerUnitTest {

    private final String TOPIC_NAME = "topic_sports_news";

    private KafkaProducer kafkaProducer;
    private MockProducer<String, String> mockProducer;

    private void buildMockProducer(boolean autoComplete) {
        this.mockProducer = new MockProducer<>(autoComplete, new StringSerializer(), new StringSerializer());
    }

    @Test
    void givenKeyValue_whenSend_thenVerifyHistory() throws ExecutionException, InterruptedException {

        buildMockProducer(true);
        //when
        kafkaProducer = new KafkaProducer(mockProducer);
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send("data", "{\"site\" : \"baeldung\"}");

        //then
        assertTrue(mockProducer.history().size() == 1);
        assertTrue(mockProducer.history().get(0).key().equalsIgnoreCase("data"));
        assertTrue(recordMetadataFuture.get().partition() == 0);

    }

    @Test
    void givenKeyValue_whenSend_thenSendOnlyAfterFlush() {

        buildMockProducer(false);
        //when
        kafkaProducer = new KafkaProducer(mockProducer);
        Future<RecordMetadata> record = kafkaProducer.send("data", "{\"site\" : \"baeldung\"}");
        assertFalse(record.isDone());

        //then
        kafkaProducer.flush();
        assertTrue(record.isDone());
    }

    @Test
    void givenKeyValue_whenSend_thenReturnException() {

        buildMockProducer(false);
        //when
        kafkaProducer = new KafkaProducer(mockProducer);
        Future<RecordMetadata> record = kafkaProducer.send("site", "{\"site\" : \"baeldung\"}");
        RuntimeException e = new RuntimeException();
        mockProducer.errorNext(e);
        //then
        try {
            record.get();
        } catch (ExecutionException | InterruptedException ex) {
            assertEquals(e, ex.getCause());
        }
        assertTrue(record.isDone());
    }

    @Test
    void givenKeyValue_whenSendWithTxn_thenSendOnlyOnTxnCommit() {

        buildMockProducer(true);
        //when
        kafkaProducer = new KafkaProducer(mockProducer);
        kafkaProducer.initTransaction();
        kafkaProducer.beginTransaction();
        Future<RecordMetadata> record = kafkaProducer.send("data", "{\"site\" : \"baeldung\"}");

        //then
        assertTrue(mockProducer.history().isEmpty());
        kafkaProducer.commitTransaction();
        assertTrue(mockProducer.history().size() == 1);
    }

    @Test
    void givenKeyValue_whenSendWithPartitioning_thenVerifyPartitionNumber() throws ExecutionException, InterruptedException {

        PartitionInfo partitionInfo0 = new PartitionInfo(TOPIC_NAME, 0, null, null, null);
        PartitionInfo partitionInfo1 = new PartitionInfo(TOPIC_NAME, 1, null, null, null);
        List<PartitionInfo> list = new ArrayList<>();
        list.add(partitionInfo0);
        list.add(partitionInfo1);
        Cluster cluster = new Cluster("kafkab", new ArrayList<Node>(), list, emptySet(), emptySet());
        this.mockProducer = new MockProducer<>(cluster, true, new EvenOddPartitioner(), new StringSerializer(), new StringSerializer());
        //when
        kafkaProducer = new KafkaProducer(mockProducer);
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send("partition", "{\"site\" : \"baeldung\"}");

        //then
        assertTrue(recordMetadataFuture.get().partition() == 1);

    }

}