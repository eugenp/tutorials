package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface IFooDao extends JpaRepository<Foo, Long>, JpaSpecificationExecutor<Foo> {

    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
    Foo retrieveByName(String name);

}
