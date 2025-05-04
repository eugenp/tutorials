package com.baeldung.kafka.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentsListener {

    private static final Logger log = LoggerFactory.getLogger(ArticleCommentsRestController.class);

    @KafkaListener(
        topics = "baeldung.article-comment.added",
        containerFactory = "customKafkaListenerContainerFactory"
    )
    public void onArticleComment(ArticleCommentAddedEvent event, @Header("traceparent") String traceParent) {
        log.info("Kafka Message Received: Comment Added: " + event);
        // some logic here...
    }

}