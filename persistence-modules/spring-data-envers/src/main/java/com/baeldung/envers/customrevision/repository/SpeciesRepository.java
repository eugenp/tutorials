package com.baeldung.envers.customrevision.repository;

import com.baeldung.envers.customrevision.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpeciesRepository extends JpaRepository<Species,Long> {

    Optional<Species> findByName(String name);
}
