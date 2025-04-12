package com.baeldung.monitoring;

import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentsListener {

    private static final Logger log = Logger.getLogger(ArticleCommentsListener.class.getName());

    @KafkaListener(topics = "baeldung.article-comment.added")
    public void onArticleComment(ArticleCommentAddedEvent event) {
        log.info("Comment added: " + event);
        // some logic here...
    }

}