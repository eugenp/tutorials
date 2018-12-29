package com.baeldung.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.domain.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
