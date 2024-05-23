package com.baeldung.spring.notamanagedtype.missedentityscan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.spring.notamanagedtype.missedentityscan.entity.CorrectEntity;

public interface CorrectEntityRepository extends JpaRepository<CorrectEntity, Long> {

}
