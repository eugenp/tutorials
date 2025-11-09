package com.baeldung.kafkastreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

public class UserStreamService {
    private static final Logger log = LoggerFactory.getLogger(UserStreamService.class);
    private KafkaStreams kafkaStreams;

    public void start(String bootstrapServer) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, User> userStream = builder.stream(
            "user-topic",
            Consumed.with(Serdes.String(), new UserSerde())
        );

        KTable<String, Long> usersPerCountry = userStream
            .filter((key, user) ->
                Objects.nonNull(user) && user.country() != null && !user.country().isEmpty())
            .groupBy((key, user) -> user.country(), Grouped.with(Serdes.String(),
                new UserSerde()))
            .count(Materialized.as("users_per_country_store"));

        usersPerCountry.toStream()
            .peek((country, count) -> log.info("Aggregated for country {} with count {}",country, count))
            .to("users_per_country", Produced.with(Serdes.String(), Serdes.Long()));

        Properties props = getStreamProperties(bootstrapServer);
        kafkaStreams = new KafkaStreams(builder.build(), props);
        kafkaStreams.setUncaughtExceptionHandler(new StreamExceptionHandler());

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

        kafkaStreams.start();
    }

    public void stop() {
        if (kafkaStreams != null) {
            kafkaStreams.close();
        }
    }

    public void cleanUp() {
        if (kafkaStreams != null) {
            kafkaStreams.cleanUp();
        }
    }

    private static Properties getStreamProperties(String bootstrapServer) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-country-aggregator" + UUID.randomUUID());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
                LogAndContinueExceptionHandler.class);
        props.put(StreamsConfig.PROCESSING_EXCEPTION_HANDLER_CLASS_CONFIG,
                CustomProcessingExceptionHandler.class);
        props.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG,
                CustomProductionExceptionHandler.class);

        return props;
    }
}

