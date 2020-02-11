package com.baeldung.hexagonal.infrastructure.persistence.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("jpa")
@Repository
public interface SongRepository extends JpaRepository<SongEntity, Integer> {

	SongEntity findBySongId(Integer productId);
}
