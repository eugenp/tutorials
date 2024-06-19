package com.baeldung.h2.tablenotfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.h2.tablenotfound.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
