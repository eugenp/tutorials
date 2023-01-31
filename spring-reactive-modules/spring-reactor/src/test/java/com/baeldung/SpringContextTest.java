package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.baeldung.reactorbus.NotificationApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotificationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringContextTest {

    @LocalServerPort
    private int port;

    @Test
    public void givenAppStarted_whenNotificationTasksSubmitted_thenProcessed() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:" + port + "/startNotification/10", String.class);
    }
}
