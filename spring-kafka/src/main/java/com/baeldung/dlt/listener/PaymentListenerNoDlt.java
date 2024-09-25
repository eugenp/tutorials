package com.baeldung.dlt.listener;

import com.baeldung.dlt.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class PaymentListenerNoDlt {
    private final Logger log = LoggerFactory.getLogger(PaymentListenerNoDlt.class);

    @RetryableTopic(attempts = "1", kafkaTemplate = "retryableTopicKafkaTemplate", dltStrategy = DltStrategy.NO_DLT)
    @KafkaListener(topics = { "payments-no-dlt" }, groupId = "payments", containerFactory="containerFactory")
    public void handlePayment(Payment payment, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Event on main topic={}, payload={}", topic, payment);
    }

    @DltHandler
    public void handleDltPayment(Payment payment, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Event on dlt topic={}, payload={}", topic, payment);
    }
}