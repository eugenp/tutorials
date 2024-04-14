package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.wrongentityscanapplication.entity.CorrectEntity;

public interface CorrectEntityRepository extends JpaRepository<CorrectEntity, Long> {

}
