package com.baeldung.envers.customrevision.repository;

import com.baeldung.envers.customrevision.domain.Pet;
import com.baeldung.envers.customrevision.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet,Long>, RevisionRepository<Pet,Long,Long> {
    List<Pet> findPetsByOwnerNullAndSpecies(Species species);
    Optional<Pet> findPetByUuid(UUID uuid);
}
