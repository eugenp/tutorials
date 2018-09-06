package com.baeldung.reactive.webflux;

import static org.mockito.BDDMockito.given;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.FluxExchangeResult;

import com.baeldung.webflux.EmployeeSpringApplication;
import com.baeldung.webflux.Employee;
import com.baeldung.webflux.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes=EmployeeSpringApplication.class)
public class EmployeeOfTheSecondControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void whenGetCurrent_thenInfiniteFluxOfEmployees() {

        Flux<Employee> dummyEmployeeFlux = Flux.fromStream(
            Stream.generate(() -> 
                generateDummyEmployee()
            )
        );
        given(employeeService.getEmployeeOfTheSecond()).willReturn(dummyEmployeeFlux);

        FluxExchangeResult<Employee> result = testClient.get()
            .uri("/employees/current")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
            .returnResult(Employee.class);

        StepVerifier.create(result.getResponseBody())
            .expectNext(generateDummyEmployee())
            .expectNextCount(5)
            .expectNext(generateDummyEmployee())
            .thenCancel()
            .verify();
    }

    private Employee generateDummyEmployee() {
        return new Employee("42", "Jane Doe");
    }
}
