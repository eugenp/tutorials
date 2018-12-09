package com.baeldung.dao.repositories;

import com.baeldung.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, PersonInsertRepository {

    @Modifying
    @Query(value = "INSERT INTO person (id, first_name, last_name) VALUES (:id,:firstName,:lastName)", nativeQuery = true)
    void insertWithAnnotation(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName);
}
