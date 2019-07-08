package com.baeldung.hexagonal.domain;

import java.util.Optional;

// This is the Inbound Port
public interface FeedReader {

    Optional<Feed> read(Long id);
}
