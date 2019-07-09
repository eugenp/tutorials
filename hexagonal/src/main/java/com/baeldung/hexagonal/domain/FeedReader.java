package com.baeldung.hexagonal.domain;

import java.util.Optional;

public interface FeedReader {

    Optional<Feed> read(Long id);
}
