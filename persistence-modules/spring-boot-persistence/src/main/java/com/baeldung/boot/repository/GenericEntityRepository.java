package com.baeldung.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.boot.domain.GenericEntity;

public interface GenericEntityRepository extends JpaRepository<GenericEntity, Long> {
}
