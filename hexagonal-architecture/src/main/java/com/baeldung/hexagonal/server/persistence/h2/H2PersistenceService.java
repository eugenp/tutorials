package com.baeldung.hexagonal.server.persistence.h2;

import com.baeldung.hexagonal.domain.ShortenedUrl;
import com.baeldung.hexagonal.domain.persistence.PersistenceService;

import java.util.Optional;

public class H2PersistenceService implements PersistenceService {

    private H2Repository repository;

    public H2PersistenceService(H2Repository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ShortenedUrl shortenedUrl) {
        repository.save(new ShortenedUrlEntity(
                shortenedUrl.getCode(),
                shortenedUrl.getUrl()
        ));
    }

    @Override
    public Optional<ShortenedUrl> getUrl(String code) {
        return repository.findByCode(code)
                .map(u -> new ShortenedUrl(u.getCode(), u.getUrl()));
    }
}
