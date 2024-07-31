package com.baeldung.spring.kafka.deserialization.exception;

import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ArticlesPublishedListener {
    private static final Logger log = Logger.getLogger(ArticlesPublishedListener.class.getName());
    private final EmailService emailService;

    public ArticlesPublishedListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "baeldung.articles.published")
    public void onArticlePublished(ArticlePublishedEvent event) {
        log.info("Received event published event: " + event);
        emailService.sendNewsletter(event.article());
    }
}
