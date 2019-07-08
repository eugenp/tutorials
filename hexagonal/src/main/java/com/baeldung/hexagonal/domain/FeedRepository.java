package com.baeldung.hexagonal.domain;

import java.util.Optional;

// This is the Outbound Port
// The implementation of the outbound port is the outbound adapter.
// Since we use Spring Data the implementation is part of the library
public interface FeedRepository {

    Optional<Feed> findById(Long id);

}
