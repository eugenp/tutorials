package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.models.Employee;
import com.baeldung.pattern.hexagonal.domain.ports.EmployeeRepository;
import com.baeldung.pattern.hexagonal.domain.ports.RepositoryException;
import com.baeldung.pattern.hexagonal.persistence.entities.EmployeeEntity;
import com.baeldung.pattern.hexagonal.persistence.mappers.EmployeeEntityMapper;
import com.baeldung.pattern.hexagonal.persistence.repositories.EmployeeJpaCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.Optional;

/**
 * The implementation of the {@link com.baeldung.pattern.hexagonal.domain.ports.EmployeeRepository} port.
 */
@Component
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeJpaCrudRepository repository;
    private final EmployeeEntityMapper employeeEntityMapper;

    @Override
    public Employee add(Employee employee) throws RepositoryException {
        EmployeeEntity incoming = employeeEntityMapper.map(employee);
        // we catch the JPA exceptions here
        try {
            EmployeeEntity outgoing = repository.save(incoming);
            return employeeEntityMapper.map(outgoing);
        } catch (DataAccessException | PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<Employee> findById(long id) throws RepositoryException {
        // we catch the JPA exceptions here
        try {
            return repository.findById(id).map(employeeEntityMapper::map);
        } catch (DataAccessException | PersistenceException e) {
            throw new RepositoryException(e);
        }
    }
}
