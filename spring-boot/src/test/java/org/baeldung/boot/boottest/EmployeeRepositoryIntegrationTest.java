package org.baeldung.boot.boottest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.baeldung.boot.boottest.Employee;
import org.baeldung.boot.boottest.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        Employee emp = new Employee("test");
        entityManager.persistAndFlush(emp);

        Optional<Employee> fromDb = employeeRepository.findByName(emp.getName());
        assertThat(fromDb.get()
            .getName()).isEqualTo(emp.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenInvalidName_thenNoSuchElementException() {
        Optional<Employee> fromDb = employeeRepository.findByName("doesNotExist");
        fromDb.get();
    }

    @Test
    public void whenFindById_thenReturnEmployee() {
        Employee emp = new Employee("test");
        entityManager.persistAndFlush(emp);

        Optional<Employee> fromDb = employeeRepository.findById(emp.getId());
        assertThat(fromDb.get()
            .getName()).isEqualTo(emp.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenInvalidId_thenNoSuchElementException() {
        Optional<Employee> fromDb = employeeRepository.findById(-11L);
        fromDb.get();
    }

    @Test
    public void givenSetOfEmployees_whenFindAll_thenReturnAllEmployees() {
        Employee alex = new Employee("alex");
        Employee ron = new Employee("ron");
        Employee bob = new Employee("bob");

        entityManager.persist(alex);
        entityManager.persist(bob);
        entityManager.persist(ron);
        entityManager.flush();

        List<Employee> allEmployees = employeeRepository.findAll();

        assertThat(allEmployees).hasSize(3)
            .extracting(Employee::getName)
            .containsOnly(alex.getName(), ron.getName(), bob.getName());
    }

}
