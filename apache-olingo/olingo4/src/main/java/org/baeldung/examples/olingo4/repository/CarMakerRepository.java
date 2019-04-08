package org.baeldung.examples.olingo4.repository;

import org.baeldung.examples.olingo4.domain.CarMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMakerRepository extends JpaRepository<CarMaker, Long>, JpaSpecificationExecutor<CarMaker>, EdmEntityRepository {

    public default String getEdmEntityName() { return CarMaker.class.getSimpleName();}
}
