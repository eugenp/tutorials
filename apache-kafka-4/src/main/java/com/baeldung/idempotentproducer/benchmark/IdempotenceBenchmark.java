package com.baeldung.idempotentproducer.benchmark;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 5, time = 20)
@Fork(2)
@State(Scope.Benchmark)
public class IdempotenceBenchmark {

    private static final String BOOTSTRAP = "localhost:29092,localhost:39092,localhost:49092";
    private static final int MESSAGES = 30000;
    private static final String TOPIC = "benchmark-topic";
    private static final int PARTITIONS = 6;
    private static final short REPLICATION_FACTOR = 3;

    @Param({ "true", "false" })
    public boolean idempotent;

    private final byte[] value = new byte[1024];

    private Producer<Long, byte[]> producer;
    private long counter = 0;

    @Setup(Level.Trial)
    public void setupTrial() throws Exception {
        counter = 0;
        createTopic();

        producer = new KafkaProducer<>(props(idempotent));

        // ensure topic is created
        producer.partitionsFor(TOPIC);
        for (int p = 0; p < PARTITIONS; p++) {
            producer.send(new ProducerRecord<>(TOPIC, p, -1L, value)).get();
        }
    }

    @TearDown(Level.Trial)
    public void shutdownTrial() {
        if (producer != null) {
            producer.close();
        }
    }

    @Benchmark
    @OperationsPerInvocation(MESSAGES)
    public void sendMessages() throws Exception {
        Future<RecordMetadata>[] futures = new Future[MESSAGES];
        for (int i = 0; i < MESSAGES; i++) {
            long key = counter++;
            futures[i] = producer.send(new ProducerRecord<>(TOPIC, key, value));
        }

        for (Future<RecordMetadata> f : futures) {
            f.get();
        }
    }

    private static Properties props(boolean idempotent) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, String.valueOf(idempotent));

        props.put(ProducerConfig.LINGER_MS_CONFIG, "5");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "30000");
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "120000");

        return props;
    }

    private static void createTopic() throws Exception {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP);

        try (AdminClient admin = AdminClient.create(props)) {
            boolean exists = admin.listTopics()
                .names()
                .get()
                .contains(TOPIC);

            if (!exists) {
                admin.createTopics(List.of(new NewTopic(TOPIC, PARTITIONS, REPLICATION_FACTOR)))
                    .all()
                    .get();
            }
        }
    }
}
