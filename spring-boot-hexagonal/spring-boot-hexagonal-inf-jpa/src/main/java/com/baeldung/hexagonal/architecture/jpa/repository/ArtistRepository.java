package com.baeldung.hexagonal.architecture.jpa.repository;

import com.baeldung.hexagonal.architecture.jpa.model.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {

        void deleteAllByName(String name);
}
