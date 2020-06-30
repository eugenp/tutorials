package com.baeldung.hexagonalarchitecture.business.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.baeldung.hexagonalarchitecture.business.entities.Concert;

public interface ConcertRepository {

  UUID save(final Concert concert);

  void delete(final UUID id);

  Optional<Concert> findById(final UUID id);

  List<Concert> findAll();
}
