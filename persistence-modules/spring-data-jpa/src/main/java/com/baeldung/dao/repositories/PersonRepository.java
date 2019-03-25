package com.baeldung.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
