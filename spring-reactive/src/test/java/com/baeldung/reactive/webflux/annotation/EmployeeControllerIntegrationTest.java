package com.baeldung.reactive.webflux.annotation;

import com.baeldung.reactive.webflux.Employee;
import com.baeldung.reactive.webflux.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes= EmployeeSpringApplication.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenCorrectEmployee() {

        Employee employee = new Employee("1", "Employee 1 Name");

        given(employeeRepository.findEmployeeById("1")).willReturn(Mono.just(employee));

        testClient.get()
            .uri("/employees/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Employee.class).isEqualTo(employee);
    }

    @Test
    public void whenGetAllEmployees_thenCorrectEmployees() {
        List<Employee> employeeList = Arrays.asList(
          new Employee("1", "Employee 1 Name"),
          new Employee("2", "Employee 2 Name"),
          new Employee("3", "Employee 3 Name")
        );
        Flux<Employee> employeeFlux = Flux.fromIterable(employeeList);

        given(employeeRepository.findAllEmployees()).willReturn(employeeFlux);

        testClient.get()
            .uri("/employees")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Employee.class).isEqualTo(employeeList);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidUser_whenUpdateEmployee_thenEmployeeUpdated() {
        Employee employee = new Employee("10", "Employee 10 Updated");

        given(employeeRepository.updateEmployee(employee)).willReturn(Mono.just(employee));

        testClient.post()
          .uri("/employees/update")
          .body(Mono.just(employee), Employee.class)
          .exchange()
          .expectStatus().isOk()
          .expectBody(Employee.class).isEqualTo(employee);

        verify(employeeRepository).updateEmployee(employee);
    }

    @Test
    @WithMockUser
    public void givenInvalidUser_whenUpdateEmployee_thenForbidden() {
        Employee employee = new Employee("10", "Employee 10 Updated");

        testClient.post()
          .uri("/employees/update")
          .body(Mono.just(employee), Employee.class)
          .exchange()
          .expectStatus().isForbidden();

        verifyNoInteractions(employeeRepository);
    }
}
