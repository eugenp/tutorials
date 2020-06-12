package com.baeldung.hexagonal.server.persistence.h2;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface H2Repository extends CrudRepository<ShortenedUrlEntity, String> {

    Optional<ShortenedUrlEntity> findByCode(String code);

}
