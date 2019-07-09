package com.baeldung.hexagonal.domain;

import java.util.Optional;

public interface FeedRepository {

    Optional<Feed> findById(Long id);
}
