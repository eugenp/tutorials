package com.baeldung.annotations;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MyUtilityRepository<Person, Long> {

    @Lock(LockModeType.NONE)
    @Query("SELECT COUNT(*) FROM Person p")
    long getPersonCount();

    @Query("FROM Person p WHERE p.name = :name")
    Person findByName(@Param("name") String name);

    @Query(value = "SELECT AVG(p.age) FROM person p", nativeQuery = true)
    int getAverageAge();

    @Procedure(name = "count_by_name")
    long getCountByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Person p SET p.name = :name WHERE p.id = :id")
    void changeName(@Param("id") long id, @Param("name") String name);

}
