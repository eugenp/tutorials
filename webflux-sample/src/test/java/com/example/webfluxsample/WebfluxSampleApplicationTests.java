package com.example.webfluxsample;

import java.util.Collections;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.webfluxsample.model.Student;
import com.example.webfluxsample.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxSampleApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    StudentRepository studentRepository;

    @Test
    public void testGetAllStudent() {
        webTestClient.get()
            .uri("/students")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Student.class);
    }

    @Test
    public void testGetStudent() {

        Student student = studentRepository.save(new Student(UUID.randomUUID()
            .toString(), "Paul", "Singapore"))
            .block();
        webTestClient.get()
            .uri("/students/{id}", Collections.singletonMap("id", student.getId()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .consumeWith(response -> Assertions.assertThat(response.getResponseBody())
                .isNotNull());
    }
}
