package com.baeldung.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.config.KafkaConfig;

public class ECommerceProducer {

    private static final Logger logger = LoggerFactory.getLogger(ECommerceProducer.class);
    private final KafkaProducer<String, String> producer;

    public ECommerceProducer() {
        this.producer = new KafkaProducer<>(KafkaConfig.createProducerProperties());
    }

    public void processCustomerEvents() {
        String customerId = "customer-123";
        String[] events = { "user_registered", "product_viewed", "item_added_to_cart", "order_placed" };

        for (String event : events) {
            ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.CUSTOMER_EVENTS_TOPIC, customerId,
                String.format("{'event': '%s', 'customer_id': '%s', 'timestamp': %d}", event, customerId, System.currentTimeMillis()));

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Customer event sent: key={}, partition={}, offset={}", customerId, metadata.partition(), metadata.offset());
                } else {
                    logger.error("Failed to send customer event", exception);
                }
            });
        }
        producer.flush();
    }

    public void processOrderLifecycle() {
        String orderId = "order-12345";
        String[] orderEvents = { "order_created", "payment_authorized", "inventory_reserved", "payment_captured", "order_shipped", "delivery_completed" };
        for (int i = 0; i < orderEvents.length; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.ORDER_EVENTS_TOPIC, orderId,
                String.format("{'event': '%s', 'order_id': '%s', 'sequence': %d, 'timestamp': %d}", orderEvents[i], orderId, i, System.currentTimeMillis()));

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.info("Order event sent: event={}, partition={}, offset={}", record.value(), metadata.partition(), metadata.offset());
                }
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread()
                    .interrupt();
            }
        }
        producer.flush();
    }

    public void demonstrateComplexEventFlow() {
        long timestamp = System.currentTimeMillis();

        producer.send(new ProducerRecord<>(KafkaConfig.CUSTOMER_EVENTS_TOPIC, "customer-456",
            "{'event': 'purchase_completed', 'order_id': 'order-789', 'timestamp': " + timestamp + "}"));

        producer.send(new ProducerRecord<>(KafkaConfig.INVENTORY_EVENTS_TOPIC, "product-123",
            "{'event': 'stock_reduced', 'order_id': 'order-789', 'timestamp': " + (timestamp + 100) + "}"));

        producer.send(new ProducerRecord<>(KafkaConfig.PAYMENT_EVENTS_TOPIC, "transaction-999",
            "{'event': 'payment_processed', 'order_id': 'order-789', 'timestamp': " + (timestamp + 200) + "}"));

        producer.flush();
        logger.info("Complex event flow demonstrated - check different partitions for related events");
    }

    public void demonstrateStickyPartitioning() {
        Map<Integer, Integer> partitionCounts = new HashMap<>();

        Properties stickyProps = KafkaConfig.createProducerProperties();
        stickyProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        stickyProps.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        stickyProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        try (KafkaProducer<String, String> stickyProducer = new KafkaProducer<>(stickyProps)) {
            for (int i = 0; i < 1000; i++) {
                String systemMetric = String.format("{'metric': 'cpu_usage', 'value': %.2f, 'host': 'server-%d', 'timestamp': %d}", Math.random() * 100,
                    (i % 10), System.currentTimeMillis());

                ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.SYSTEM_METRICS_TOPIC, null, systemMetric);

                stickyProducer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        partitionCounts.merge(metadata.partition(), 1, Integer::sum);
                    }
                });
            }
            stickyProducer.flush();
        }

        logger.info("Sticky partitioning complete. Final distribution: {}", partitionCounts);
    }

    public void close() {
        producer.close();
    }
}