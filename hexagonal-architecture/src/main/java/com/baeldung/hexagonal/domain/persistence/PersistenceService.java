package com.baeldung.hexagonal.domain.persistence;

import com.baeldung.hexagonal.domain.ShortenedUrl;

import java.util.Optional;

public interface PersistenceService {

    void save(ShortenedUrl shortenedUrl);

    Optional<ShortenedUrl> getUrl(String code);

}
