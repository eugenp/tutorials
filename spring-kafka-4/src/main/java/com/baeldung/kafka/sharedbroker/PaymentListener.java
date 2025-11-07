package com.baeldung.kafka.sharedbroker;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentListener.class);
    private static final CountDownLatch latch = new CountDownLatch(1);

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @KafkaListener(topics = "payment")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws Exception {
        try (AdminClient admin = AdminClient.create(Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers))) {
            LOG.info("Received payment request [{}] from broker [{}]", consumerRecord.value(), admin.describeCluster()
                .clusterId()
                .get());
        }
        latch.countDown();
    }

    public static CountDownLatch getLatch() {
        return latch;
    }

}
