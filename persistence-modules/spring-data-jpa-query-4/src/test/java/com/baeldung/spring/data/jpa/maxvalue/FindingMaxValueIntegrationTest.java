package com.baeldung.spring.data.jpa.maxvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {FindingMaxValueIntegrationTest.class, EmployeeMaxValueService.class})
@EntityScan(basePackageClasses = Employee.class)
@EnableJpaRepositories(basePackageClasses = EmployeeRepository.class)
public class FindingMaxValueIntegrationTest {

    @Autowired
    private EmployeeMaxValueService employeeMaxValueService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final static Long EXPECTED_VALUE = 800L;

    @BeforeEach
    void givenEmployees() {
        Employee employee1 = new Employee(1, "John", 400L);
        Employee employee2 = new Employee(2, "Paul", 800L);
        Employee employee3 = new Employee(3, "Jack", 600L);

        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
    }

    @Test
    void givenEmployees_whenFindMaxUsingDerivedQuery_thenMaxReturned() {
        Optional<Employee> result = employeeRepository.findTopByOrderBySalaryDesc();

        assertThat(result)
            .isNotEmpty()
            .containsInstanceOf(Employee.class)
            .get().extracting(Employee::getSalary)
            .isEqualTo(EXPECTED_VALUE);
    }

    @Test
    void givenEmployees_whenFindMaxUsingDerivedQueryWithProjection_thenMaxReturned() {
        Optional<EmployeeSalary> result = employeeRepository.findTopSalaryByOrderBySalaryDesc();

        assertThat(result)
            .isNotEmpty()
            .containsInstanceOf(EmployeeSalary.class)
            .get().extracting(EmployeeSalary::getSalary)
            .isEqualTo(EXPECTED_VALUE);
    }

    @Test
    void givenEmployees_whenFindMaxUsingJPQL_thenMaxReturned() {
        Optional<Long> result = employeeRepository.findTopSalaryJpql();

        assertThat(result)
            .hasValue(EXPECTED_VALUE);
    }

    @Test
    void givenEmployees_whenFindMaxUsingNativeQuery_thenMaxReturned() {
        Optional<Long> result = employeeRepository.findTopSalaryNative();

        assertThat(result)
            .hasValue(EXPECTED_VALUE);
    }

    @Test
    void givenEmployees_whenFindMaxUsingCustomMethod_thenMaxReturned() {
        Optional<Long> result = employeeRepository.findTopSalaryCustomMethod();

        assertThat(result)
            .hasValue(EXPECTED_VALUE);
    }

    @Test
    void givenEmployees_whenFindMaxUsingPageable_thenMaxReturned() {
        Optional<Long> result = employeeRepository.findTopSalaryPageable();

        assertThat(result)
            .hasValue(EXPECTED_VALUE);
    }

    @Test
    void givenEmployees_whenFindMaxUsingCriteria_thenMaxReturned() {
        Optional<Long> result = employeeMaxValueService.findMaxSalaryCriteriaAPI();

        assertThat(result)
            .hasValue(EXPECTED_VALUE);
    }
}
