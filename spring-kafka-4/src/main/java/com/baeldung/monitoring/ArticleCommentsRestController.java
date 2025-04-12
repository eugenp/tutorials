package com.baeldung.monitoring;

import java.util.logging.Logger;

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

    private final KafkaTemplate<String, ArticleCommentAddedEvent> kafkaTemplate;

    public ArticleCommentsRestController(KafkaTemplate<String, ArticleCommentAddedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/articles/{articleSlug}/comments")
    void addArticleComment(@PathVariable("articleSlug") String articleSlug, @RequestBody ArticleCommentAddedDto dto) {
        log.info("Comment added: " + dto);
        // some logic here (eg: save to DB)

        var event = new ArticleCommentAddedEvent(articleSlug, dto.articleAuthor(), dto.comment(), dto.commentAuthor());
        kafkaTemplate.send("baeldung.article-comment.added", articleSlug, event);
    }

    record ArticleCommentAddedDto(String articleAuthor, String comment, String commentAuthor) {

    }
}