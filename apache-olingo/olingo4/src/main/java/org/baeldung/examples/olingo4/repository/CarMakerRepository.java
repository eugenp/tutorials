package org.baeldung.examples.olingo4.repository;

import org.baeldung.examples.olingo4.domain.CarMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMakerRepository extends EdmEntityRepository<CarMaker>, JpaRepository<CarMaker, Long>, JpaSpecificationExecutor<CarMaker> {

    public default String getEdmEntityName() { return CarMaker.class.getSimpleName();}
    @Override
    default Class<CarMaker> getEntityClass() {
        return CarMaker.class;
    }
}
