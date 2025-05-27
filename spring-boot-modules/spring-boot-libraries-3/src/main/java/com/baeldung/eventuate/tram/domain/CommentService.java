package com.baeldung.eventuate.tram.domain;

import static java.util.Collections.singletonList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import jakarta.transaction.Transactional;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository comments;
    private final DomainEventPublisher domainEvents;
    private final KafkaTemplate<Long, CommentAddedEvent> kafkaTemplate;

    public CommentService(CommentRepository commentRepository, DomainEventPublisher domainEvents, KafkaTemplate<Long, CommentAddedEvent> kafkaTemplate) {
        this.comments = commentRepository;
        this.domainEvents = domainEvents;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Long save(Comment comment) {
        Comment saved = this.comments.save(comment);
        log.info("Comment created: {}", saved);

        CommentAddedEvent commentAdded = new CommentAddedEvent(saved.getId(), saved.getArticleSlug());
        domainEvents.publish(
            "baeldung.comment.added",
            saved.getId(),
            singletonList(commentAdded)
        );
        return saved.getId();
    }

}
