package com.baeldung.spring.modulith.events.externalization.infra;

import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;

import com.baeldung.spring.modulith.events.externalization.ArticlePublishedEvent;

//@Component
// this is used in sections 3 and 4 of tha article
// but it will cause the tests to fail if it used together with the @Externalized annotation
class ArticlePublishedKafkaProducer {

    private final KafkaOperations<String, ArticlePublishedEvent> messageProducer;

    public ArticlePublishedKafkaProducer(KafkaOperations<String, ArticlePublishedEvent> messageProducer) {
        this.messageProducer = messageProducer;
    }

    @EventListener
    public void publish(ArticlePublishedEvent event) {
        Assert.notNull(event.slug(), "Article Slug must not be null!");
        messageProducer.send("baeldung.articles.published", event);
    }

    @Async
    @TransactionalEventListener
    public void publishAsync(ArticlePublishedEvent article) {
        Assert.notNull(article.slug(), "Article Slug must not be null!");
        messageProducer.send("baeldung.articles.published", article.slug(), article);
    }
}
