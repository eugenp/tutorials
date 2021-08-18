package com.baeldung.kafka.streamsvsconsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KGroupedTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.WindowStore;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

public class KafkaStreamsLiveTest {
    private final String LEFT_TOPIC = "left-stream-topic";
    private final String RIGHT_TOPIC = "right-stream-topic";
    private final String LEFT_RIGHT_TOPIC = "left-right-stream-topic";

    private KafkaProducer<String, String> producer = createKafkaProducer();
    private Properties streamsConfiguration = new Properties();

    static final String TEXT_LINES_TOPIC = "TextLinesTopic";

    private final String TEXT_EXAMPLE_1 = "test test and test";
    private final String TEXT_EXAMPLE_2 = "test filter filter this sentence";

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Before
    public void setUp() {
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    @Test
    public void shouldTestKafkaTableLatestWord() throws InterruptedException {
        String inputTopic = "topicTable";

        final StreamsBuilder builder = new StreamsBuilder();

        KTable<String, String> textLinesTable = builder.table(inputTopic,
            Consumed.with(Serdes.String(), Serdes.String()));

        textLinesTable.toStream().foreach((word, count) -> System.out.println("Latest word: " + word + " -> " + count));

        final Topology topology = builder.build();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "latest-word-id");
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();
        producer.send(new ProducerRecord<String, String>(inputTopic, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(inputTopic, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        streams.close();
    }

    @Test
    public void shouldTestWordCountKafkaStreams() throws InterruptedException {
        String wordCountTopic = "wordCountTopic";

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(wordCountTopic,
          Consumed.with(Serdes.String(), Serdes.String()));

        KTable<String, Long> wordCounts = textLines
          .flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.ROOT)
            .split("\\W+")))
          .groupBy((key, word) -> word)
          .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>> as("counts-store"));

        wordCounts.toStream().foreach((word, count) -> System.out.println("Word: " + word + " -> " + count));

        wordCounts.toStream().to("outputTopic",
          Produced.with(Serdes.String(), Serdes.Long()));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-stream-table-id");
        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(wordCountTopic, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(wordCountTopic, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        streams.close();
    }

    // Filter, map
    @Test
    public void shouldTestStatelessTransformations() throws InterruptedException {
        String wordCountTopic = "wordCountTopic";

        //when
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(wordCountTopic,
          Consumed.with(Serdes.String(), Serdes.String()));

        final KStream<String, String> textLinesUpperCase =
          textLines
            .map((key, value) -> KeyValue.pair(value, value.toUpperCase()))
            .filter((key, value) -> value.contains("FILTER"));

        KTable<String, Long> wordCounts = textLinesUpperCase
          .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
          .groupBy((key, word) -> word)
          .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>> as("counts-store"));

        wordCounts.toStream().foreach((word, count) -> System.out.println("Word: " + word + " -> " + count));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-filter-map-id");
        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(wordCountTopic, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(wordCountTopic, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        streams.close();

    }

    @Test
    public void shouldTestAggregationStatefulTransformations() throws InterruptedException {
        String aggregationTopic = "aggregationTopic";

        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<byte[], String> input = builder.stream(aggregationTopic,
          Consumed.with(Serdes.ByteArray(), Serdes.String()));
        final KTable<String, Long> aggregated = input
          .groupBy((key, value) -> (value != null && value.length() > 0) ? value.substring(0, 2).toLowerCase() : "",
            Grouped.with(Serdes.String(), Serdes.String()))
          .aggregate(() -> 0L, (aggKey, newValue, aggValue) -> aggValue + newValue.length(),
            Materialized.with(Serdes.String(), Serdes.Long()));

        aggregated.toStream().foreach((word, count) -> System.out.println("Word: " + word + " -> " + count));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "aggregation-id");
        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(aggregationTopic, "1", "one"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "2", "two"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "3", "three"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "4", "four"));
        producer.send(new ProducerRecord<String, String>(aggregationTopic, "5", "five"));

        Thread.sleep(5000);
        streams.close();

    }

    @Test
    public void shouldTestWindowingJoinStatefulTransformations() throws InterruptedException {
        final StreamsBuilder builder = new StreamsBuilder();

       KStream<String, String> leftSource = builder.stream(LEFT_TOPIC);
       KStream<String, String> rightSource = builder.stream(RIGHT_TOPIC);

        KStream<String, String> leftRightSource = leftSource.outerJoin(rightSource,
         (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue,
         JoinWindows.of(Duration.ofSeconds(5)))
           .groupByKey()
           .reduce(((key, lastValue) -> lastValue))
           .toStream();

        leftRightSource.foreach((key, value) -> System.out.println("(key= " + key + ") -> (" + value + ")"));

        final Topology topology = builder.build();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "windowing-join-id");
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(LEFT_TOPIC, "1", "left"));
        producer.send(new ProducerRecord<String, String>(RIGHT_TOPIC, "2", "right"));

        Thread.sleep(2000);
        streams.close();
    }

    @Test
    public void shouldTestWordCountWithInteractiveQueries() throws InterruptedException {

        final Serde<String> stringSerde = Serdes.String();
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String>
          textLines = builder.stream(TEXT_LINES_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        final KGroupedStream<String, String> groupedByWord = textLines
          .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
          .groupBy((key, word) -> word, Grouped.with(stringSerde, stringSerde));

        groupedByWord.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("WordCountsStore")
          .withValueSerde(Serdes.Long()));

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-interactive-queries");

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.cleanUp();
        streams.start();

        producer.send(new ProducerRecord<String, String>(TEXT_LINES_TOPIC, "1", TEXT_EXAMPLE_1));
        producer.send(new ProducerRecord<String, String>(TEXT_LINES_TOPIC, "2", TEXT_EXAMPLE_2));

        Thread.sleep(2000);
        ReadOnlyKeyValueStore<String, Long> keyValueStore =
          streams.store(StoreQueryParameters.fromNameAndType(
            "WordCountsStore", QueryableStoreTypes.keyValueStore()));

        KeyValueIterator<String, Long> range = keyValueStore.all();
        while (range.hasNext()) {
            KeyValue<String, Long> next = range.next();
            System.out.println("Count for " + next.key + ": " + next.value);
        }

        streams.close();
    }

    private static KafkaProducer<String, String> createKafkaProducer() {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer(props);

    }
}


