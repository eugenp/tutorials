package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.domain.dto.CreatePersonDto;

import java.util.UUID;

public interface PersonPort {

    void save(UUID id, CreatePersonDto request);

    Person getById(UUID id);
}
