package com.baeldung.spring.kafka.streams;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
    public java.util.function.Consumer<KStream<String, String>> process() {
        return inputStream ->
            inputStream.foreach((key, value) -> {
                LOGGER.info("Key: " + key + " Value: " + value);
            });
    }

    //@Bean
    public Function<KStream<String, String>, KStream<String, String>> process2() {
        return inputStream -> inputStream
            .map((key, value) -> {
                LOGGER.info("Key: " + key + " Value: " + value);
                return new KeyValue<>(key, value.toUpperCase());
            });
    }

    @Bean
    public BiFunction<KStream<Long, Long>, KTable<Long, Long>, KStream<Long, Long>> store() {
        return (customerOrderStream, customerShopTable)
            -> (customerOrderStream.leftJoin(customerShopTable, (count, shop)
                -> new ShopOrders(shop == null ? 1L : shop, count),
            Joined.with(Serdes.Long(), Serdes.Long(), null))
            .map((customer, shopWithCustomer)
                -> new KeyValue<>(shopWithCustomer.getShop(), shopWithCustomer.getCount()))
            .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()))
            .reduce(Long::sum).toStream());
    }

    //@Bean
    public BiConsumer<KStream<String, Long>, KTable<String, String>> store2() {
        return (customerOrderStream, customerShopTable) -> {
            customerOrderStream.foreach((key, value) -> LOGGER.info("Key: " + key + " Value: " + value));
            customerShopTable.toStream().foreach((key, value) -> LOGGER.info("Key: " + key + " Value: " + value));
        };
    }


    //@Bean
    public Function<KStream<String, Long>, KStream<String, Transaction>[]> transactions() {

        Predicate<String, Transaction> isDebit = (key, value) -> value.type.equals("DEBIT");
        Predicate<String, Transaction> isCredit = (key, value) -> value.type.equals("CREDIT");

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

    class ShopOrders {
        private Long shop;
        private long count;

        public ShopOrders(final Long shop, final long count) {
            this.shop = shop;
            this.count = count;
        }

        public Long getShop() {
            return shop;
        }

        public void setShop(final Long shop) {
            this.shop = shop;
        }

        public long getCount() {
            return count;
        }

        public void setCount(final long count) {
            this.count = count;
        }
    }

    class Transaction {
        private String type;
        private long amount;

        public Transaction(final String type, final long amount) {
            this.type = type;
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(final long amount) {
            this.amount = amount;
        }
    }

    class CustomValue {
        private String name;

        public CustomValue(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}
