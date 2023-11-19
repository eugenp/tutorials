package com.baeldung.spring.amqp;

import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;


public class SpringWebfluxAmqpLiveTest {

    @Test
    public void whenSendingAMessageToQueue_thenAcceptedReturnCode() {

        WebTestClient client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();
        
        client.post()
            .uri("/queue/NYSE")
            .syncBody("Test Message")
            .exchange()
            .expectStatus().isAccepted();

    }
    
    

}
