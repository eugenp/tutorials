package com.baeldung.springdatajdbcintro.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.springdatajdbcintro.entity.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {

    @Query("select * from person where first_name=:firstName")
    List<Person> findByFirstName(@Param("firstName") String firstName);

    @Modifying
    @Query("UPDATE person SET first_name = :name WHERE id = :id")
    boolean updateByFirstName(@Param("id") Long id, @Param("name") String name);

}
