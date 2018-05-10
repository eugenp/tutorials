package com.example.demo.reactive.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReactiveWebfluxServerTest {

    private WebTestClient webTestClient;

    @Before
    public void before() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }


    @Test
    public void eventById() throws Exception {
        this.webTestClient.get().uri("/events/42").accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk();
    }

}