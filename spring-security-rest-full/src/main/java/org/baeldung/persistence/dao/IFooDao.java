package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFooDao extends JpaRepository<Foo, Long>, JpaSpecificationExecutor<Foo> {
    //
}
