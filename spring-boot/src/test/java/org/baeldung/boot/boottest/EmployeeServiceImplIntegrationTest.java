package org.baeldung.boot.boottest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.baeldung.boot.boottest.Employee;
import org.baeldung.boot.boottest.EmployeeRepository;
import org.baeldung.boot.boottest.EmployeeService;
import org.baeldung.boot.boottest.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee john = new Employee("john");
        john.setId(11L);

        Optional<Employee> emp = Optional.of(john);

        Employee bob = new Employee("bob");
        Employee alex = new Employee("alex");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName()))
            .thenReturn(emp);
        Mockito.when(employeeRepository.findByName("wrong_name"))
            .thenReturn(Optional.empty());
        Mockito.when(employeeRepository.findById(john.getId()))
            .thenReturn(emp);
        Mockito.when(employeeRepository.findAll())
            .thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L))
            .thenReturn(Optional.empty());
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        Optional<Employee> fromDb = employeeService.getEmployeeByName("john");
        assertThat(fromDb.get()
            .getName()).isEqualTo("john");

        verifyFindByNameIsCalledOnce("john");
    }

    @Test(expected = NoSuchElementException.class)
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        Optional<Employee> fromDb = employeeService.getEmployeeByName("wrong_name");
        fromDb.get();

        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidName_thenEmployeeShouldExist() {
        boolean doesEmployeeExist = employeeService.exists("john");
        assertThat(doesEmployeeExist).isEqualTo(true);

        verifyFindByNameIsCalledOnce("john");
    }

    @Test
    public void whenNonExistingName_thenEmployeeShouldNotExist() {
        boolean doesEmployeeExist = employeeService.exists("some_name");
        assertThat(doesEmployeeExist).isEqualTo(false);

        verifyFindByNameIsCalledOnce("some_name");
    }

    @Test
    public void whenValidI_thendEmployeeShouldBeFound() {
        Optional<Employee> fromDb = employeeService.getEmployeeById(11L);
        assertThat(fromDb.get()
            .getName()).isEqualTo("john");

        verifyFindByIdIsCalledOnce();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenInValidId_thenEmployeeShouldNotBeFound() {
        Optional<Employee> fromDb = employeeService.getEmployeeById(-99L);
        verifyFindByIdIsCalledOnce();
        fromDb.get();
    }

    @Test
    public void given3Employees_whengetAll_thenReturn3Records() {
        Employee alex = new Employee("alex");
        Employee john = new Employee("john");
        Employee bob = new Employee("bob");

        List<Employee> allEmployees = employeeService.getAllEmployees();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3)
            .extracting(Employee::getName)
            .contains(alex.getName(), john.getName(), bob.getName());
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1))
            .findByName(name);
        Mockito.reset(employeeRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1))
            .findById(Mockito.anyLong());
        Mockito.reset(employeeRepository);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1))
            .findAll();
        Mockito.reset(employeeRepository);
    }
}
