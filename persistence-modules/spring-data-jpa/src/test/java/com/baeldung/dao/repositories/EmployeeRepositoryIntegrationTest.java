package com.baeldung.dao.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.domain.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    private static final Employee EMPLOYEE1 = new Employee(1L, "John");
    private static final Employee EMPLOYEE2 = new Employee(2L, "Alice");

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployeeEntity_whenInsertWithSave_ThenEmployeeIsPersisted() {
        employeeRepository.save(EMPLOYEE1);
        assertEmployeePersisted(EMPLOYEE1);
    }
    
    @Test
    public void givenEmployeeEntity_whenInsertWithSaveAndFlush_ThenEmployeeIsPersisted() {
        employeeRepository.saveAndFlush(EMPLOYEE2);
        assertEmployeePersisted(EMPLOYEE2);
    }

    private void assertEmployeePersisted(Employee input) {
        Employee employee = employeeRepository.getOne(input.getId());
        assertThat(employee).isNotNull();
    }
}
