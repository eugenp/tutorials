package com.baeldung.spring.kafka.streams;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class KafkaStreamsConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamsConfiguration.class);

    //@Bean
    public Consumer<KStream<String, String>> process() {
        return inputStream ->
            inputStream.foreach((country, capital) -> {
                LOGGER.info("Country: " + country + " Capital: " + country);
            });
    }

    //@Bean
    public Function<KStream<String, String>, KStream<String, String>> inputOutput() {
        return inputStream -> inputStream
            .map((country, capital) -> {
                LOGGER.info("Country: " + country + " Country: " + capital);
                return new KeyValue<>(country, capital.toUpperCase());
            });
    }

    @Bean
    public BiFunction<KStream<Long, Long>, KTable<Long, Long>, KStream<Long, Long>> store() {
        return (customerOrderStream, customerShopTable)
            -> leftJoin(customerOrderStream, customerShopTable)
            .map((customer, shopWithCustomer)
                -> new KeyValue<>(shopWithCustomer.getShop(), shopWithCustomer.getCount()))
            .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()))
            .reduce(Long::sum).toStream();
    }

    private KStream<Long, ShopOrders> leftJoin(KStream<Long, Long> customerOrderStream,
        KTable<Long, Long> customerShopTable) {
        return customerOrderStream.leftJoin(customerShopTable, (count, shop)
                -> new ShopOrders(shop == null ? 1L : shop, count),
            Joined.with(Serdes.Long(), Serdes.Long(), null));
    }

    //@Bean
    public BiConsumer<KStream<String, Long>, KTable<String, String>> incomingStore() {
        return (customerOrderStream, customerShopTable) -> {
            customerOrderStream.foreach((key, value) -> LOGGER.info("Key: " + key + " Value: " + value));
            customerShopTable.toStream().foreach((key, value) -> LOGGER.info("Key: " + key + " Value: " + value));
        };
    }


    //@Bean
    public Function<KStream<String, Long>, KStream<String, Transaction>[]> transactions() {

        Predicate<String, Transaction> isDebit = (key, value) -> value.getType().equals("DEBIT");
        Predicate<String, Transaction> isCredit = (key, value) -> value.getType().equals("CREDIT");

        return input -> input
            .map((key, value) ->
                new KeyValue<>(UUID.randomUUID().toString(), new Transaction(key, value)))
            .branch(isDebit, isCredit);
    }

    //@Bean
    public java.util.function.Consumer<KStream<String, CustomValue>> custom() {
        return inputStream ->
            inputStream.foreach((key, value) -> {
                LOGGER.info("Key: " + key + " Value: " + value.getName());
            });
    }

    //@Bean
    public Serde<CustomValue> customValueSerde() {
        return new CustomValueSerde();
    }

}
