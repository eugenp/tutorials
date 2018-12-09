package com.baeldung.dao.repositories;

import com.baeldung.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonCustomRepository extends JpaRepository<Person, Long>, InsertRepository<Person> {

}
