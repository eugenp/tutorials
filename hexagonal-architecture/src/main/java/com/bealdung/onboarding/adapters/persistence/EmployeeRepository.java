package com.bealdung.onboarding.adapters.persistence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
    @Override
    Optional<EmployeeEntity> findById(Long id);
    @Override
    <S extends EmployeeEntity> S save(S s);
    @Override
    void delete(EmployeeEntity employeeEntity);
}

