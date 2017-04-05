package com.baeldung.springamqpsimple;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MessageControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    public void whenPostingMessage_thenMessageIsCreated() {
        final String message = "Hello World!";
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/messages", message, Void.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void whenPostingMessage_thenMessageIsSentToBroker() {
        final String message = "Hello World!";
        restTemplate.postForEntity("/messages", message, Void.class);

        verify(rabbitTemplate).convertAndSend(SpringAmqpConfig.queueName, message);
    }
}