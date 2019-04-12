package org.baeldung.examples.olingo4.repository;

import java.util.List;

import org.baeldung.examples.olingo4.domain.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends EdmEntityRepository<CarModel>, JpaRepository<CarModel, Long>, JpaSpecificationExecutor<CarModel> {

    public List<CarModel> findByMakerId(Long makerId);
    
    public default String getEdmEntityName() { return CarModel.class.getSimpleName();}
    
    @Override
    default Class<CarModel> getEntityClass() {
        return CarModel.class;
    }

}
