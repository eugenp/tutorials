package com.baeldung.envers.customrevision.repository;

import com.baeldung.envers.customrevision.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
    Optional<Owner> findByName(String name);
}
