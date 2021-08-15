package com.baeldung.kafkastreams;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.junit.Ignore;
import org.junit.Test;

public class KafkaStreamsLiveTest {
    private String bootstrapServers = "localhost:9092";

    @Test
    @Ignore("it needs to have kafka broker running on local")
    public void shouldTestKafkaStreams() throws InterruptedException {
        //given
        String inputTopic = "inputTopic";

        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-live-test");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Use a temporary directory for storing state, which will be automatically removed after the test.
      //  streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, TestUtils.tempDirectory().getAbsolutePath());
        
        /*
         * final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(wordCountTopic,
          Consumed.with(Serdes.String(), Serdes.String()));

        KTable<String, Long> wordCounts = textLines
          .flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.ROOT)
            .split("\\W+")))
          .groupBy((key, word) -> word)
          .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>> as("counts-store"));
         */
        //when
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(inputTopic);
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        KTable<String, Long> wordCounts = textLines
                .flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                .groupBy((key, word) -> word)
                .count();

        wordCounts.toStream().foreach((word, count) -> System.out.println("word: " + word + " -> " + count));

        String outputTopic = "outputTopic";
        //final Serde<String> stringSerde = Serdes.String();
        //final Serde<Long> longSerde = Serdes.Long();
        //wordCounts.toStream().to(stringSerde, longSerde, outputTopic);
        
        wordCounts.toStream().to("outputTopic",
            Produced.with(Serdes.String(), Serdes.Long()));

        final Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);
        streams.start();

        //then
        Thread.sleep(30000);
        streams.close();
    }
}
