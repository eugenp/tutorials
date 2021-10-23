package com.bealdung.hexagonal.architecture.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bealdung.hexagonal.architecture.infrastructure.jpa.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

}
