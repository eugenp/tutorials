package com.baeldung.examples.r2dbc.flyway.rest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.baeldung.examples.r2dbc.flyway.model.Department;
import com.baeldung.examples.r2dbc.flyway.model.Student;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentResourceUnitTest {

    private static final String DEPARTMENT_ENDPOINT = "/department";
    private static final String STUDENT_ENDPOINT = "/student";

    @Autowired
    protected WebTestClient webTestClient;

    @Test
    void givenDepartmentExists_WhenCreateStudentRequestIsSent_ShouldBeProcessedSuccessfully() {

        // Given
        List<Department> departmentList = webTestClient.get()
          .uri(DEPARTMENT_ENDPOINT)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(Department.class)
          .returnResult()
          .getResponseBody();

        Assertions.assertNotNull(departmentList);

        // When
        Student student = webTestClient.post()
          .uri(STUDENT_ENDPOINT)
          .body(BodyInserters.fromPublisher(Mono.just(Student.builder()
            .firstName("John")
            .lastName("Doe")
            .dateOfBirth(LocalDate.of(2015, 12, 1))
            .department(departmentList.get(0)
              .getId())
            .build()), Student.class))
          .exchange()
          .expectStatus()
          .isEqualTo(201)
          .expectBody(Student.class)
          .returnResult()
          .getResponseBody();

        // Then
        Assertions.assertNotNull(student.getId());
        Assertions.assertEquals("John", student.getFirstName());
        Assertions.assertEquals("Doe", student.getLastName());
        Assertions.assertEquals(LocalDate.of(2015, 12, 1), student.getDateOfBirth());
    }

}