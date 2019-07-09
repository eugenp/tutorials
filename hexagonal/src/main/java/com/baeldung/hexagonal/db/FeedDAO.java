package com.baeldung.hexagonal.db;

import com.baeldung.hexagonal.domain.Feed;
import com.baeldung.hexagonal.domain.FeedRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Transactional
@Repository
public class FeedDAO implements FeedRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Feed> findById(Long id) {
        Feed feed = entityManager.find(Feed.class, id);
        return Optional.of(feed);
    }
}
