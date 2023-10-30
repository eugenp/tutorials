package com.baeldung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface IFooDAO extends JpaRepository<Foo, Long> {

    Foo findByName(String name);

    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
    Foo retrieveByName(@Param("name") String name);
}
