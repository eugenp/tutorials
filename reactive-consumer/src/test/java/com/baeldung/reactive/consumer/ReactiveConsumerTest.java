package com.baeldung.reactive.consumer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveConsumerTest {
       
        private static final String SERVER = "http://localhost:8080/stockprice";
    
        @Autowired
        private WebTestClient webTestClient;

        @Test
        public void testHello() {
                webTestClient
                        // Create a GET request to test an endpoint
                        .get().uri(SERVER)
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk();

        }
}


