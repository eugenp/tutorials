package com.baeldung.boot.daos;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.baeldung.boot.domain.Location;

@org.springframework.stereotype.Repository
public interface ReadOnlyLocationRepository extends Repository<Location, Long> {

    Optional<Location> findById(Long id);

    Location save(Location location);
}
