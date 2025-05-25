package com.baeldung.eventuate.tram.domain;

import static java.util.Collections.singletonList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.eventuate.tram.events.publisher.DomainEventPublisher;
import jakarta.transaction.Transactional;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository comments;
    private final DomainEventPublisher domainEvents;

    public CommentService(CommentRepository commentRepository, DomainEventPublisher domainEvents) {
        this.comments = commentRepository;
        this.domainEvents = domainEvents;
    }

    @Transactional
    public Long save(Comment comment) {
        Comment saved = this.comments.save(comment);
        log.info("Comment created: {}", saved);

        CommentAddedEvent commentAdded = new CommentAddedEvent(saved.getId(), saved.getArticleSlug());
        domainEvents.publish(
            Comment.class,
            saved.getId(),
            singletonList(commentAdded)
        );

        return saved.getId();
    }
}
