package com.baeldung.hexagonalarchitecture.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.baeldung.hexagonalarchitecture.business.entities.Concert;
import com.baeldung.hexagonalarchitecture.business.repository.ConcertRepository;

public class InMemoryConcertRepository implements ConcertRepository {

    private final List<Concert> concerts = new ArrayList<>();

    @Override
    public UUID save(final Concert concert) {
        final Optional<Concert> existingConcert =
            concerts.stream().filter(c -> c.getId().equals(concert.getId())).findFirst();

        if (existingConcert.isPresent()) {
            delete(existingConcert.get().getId());
        }

        concerts.add(concert);
        return concert.getId();
    }

    @Override
    public void delete(final UUID id) {
        concerts.removeIf(concert -> concert.getId().equals(id));
    }

    @Override
    public Optional<Concert> findById(final UUID id) {
        return Optional
            .ofNullable(concerts.stream().filter(concert -> concert.getId().equals(id)).findFirst().orElse(null));
    }

    @Override
    public List<Concert> findAll() {
        return concerts;
    }
}
