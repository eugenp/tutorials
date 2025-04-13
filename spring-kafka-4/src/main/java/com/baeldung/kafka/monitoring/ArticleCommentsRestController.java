package com.baeldung.kafka.monitoring;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ArticleCommentsRestController {

    private static final Logger log = Logger.getLogger(ArticleCommentsRestController.class.getName());

    private final KafkaTemplate<String, ArticleCommentAddedEvent> articleCommentsKafkaTemplate;

    public ArticleCommentsRestController(
        @Qualifier("articleCommentsKafkaTemplate") KafkaTemplate<String, ArticleCommentAddedEvent> articleCommentsKafkaTemplate) {
        this.articleCommentsKafkaTemplate = articleCommentsKafkaTemplate;
    }

    @PostMapping("/articles/{articleSlug}/comments")
    Response addArticleComment(@PathVariable("articleSlug") String articleSlug, @RequestBody ArticleCommentAddedDto dto) {
        log.info("Comment added: " + dto);
        // some logic here (eg: save to DB)

        var event = new ArticleCommentAddedEvent(articleSlug, dto.articleAuthor(), dto.comment(), dto.commentAuthor());
        articleCommentsKafkaTemplate.send("baeldung.article-comment.added", articleSlug, event);

        return new Response("Success", articleSlug);
    }

    record Response(String status, String articleSlug) {

    }

    record ArticleCommentAddedDto(String articleAuthor, String comment, String commentAuthor) {

    }
}