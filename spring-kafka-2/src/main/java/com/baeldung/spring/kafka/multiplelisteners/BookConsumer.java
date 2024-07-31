package com.baeldung.spring.kafka.multiplelisteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BookConsumer.class);

    @KafkaListener(topics = "books", groupId = "books-content-search")
    public void bookContentSearchConsumer(BookEvent event) {
        logger.info("Books event received for full-text search indexing => {}", event);
    }

    @KafkaListener(topics = "books", groupId = "books-price-index")
    public void bookPriceIndexerConsumer(BookEvent event) {
        logger.info("Books event received for price indexing => {}", event);
    }

    @KafkaListener(topics = "books", groupId = "book-notification-consumer", concurrency = "2")
    public void bookNotificationConsumer(BookEvent event) {
        logger.info("Books event received for notification => {}", event);
    }
}
