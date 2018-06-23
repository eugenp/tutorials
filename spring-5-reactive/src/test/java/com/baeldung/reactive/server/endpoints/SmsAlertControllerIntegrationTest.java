package com.baeldung.reactive.server.endpoints;

import com.baeldung.reactive.events.SmsEvent;
import com.baeldung.reactive.server.SmsAlertReactiveServerApplication;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmsAlertReactiveServerApplication.class)
public class SmsAlertControllerIntegrationTest {

    private static WebTestClient client;

    @BeforeClass
    public static void setUp() {
        client = WebTestClient.bindToController(new SmsAlertController())
                .build();
    }

    @Test
    public void givenMobileNumber_whenPushNotifications_thenPushSmsEvents() throws Exception {
        client.get()
                .uri("/api/short-messages/773303584")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(SmsEvent.class);
    }

}