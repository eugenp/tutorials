package org.baeldung.examples.olingo4.repository;

import org.baeldung.examples.olingo4.domain.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long>, JpaSpecificationExecutor<CarModel>,EdmEntityRepository {

    public default String getEdmEntityName() { return CarModel.class.getSimpleName();}

}
