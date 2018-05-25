package com.baeldung.functional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.baeldung.functional.model.CityAirport;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5JavaApplication.class)
public class Spring5JavaApplicationIntegrationTest {

    private static WebTestClient client;

    @BeforeClass
    public static void setup() throws Exception {
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:9000")
            .build();
    }

    @Test
    public void givenRouter_whenGetTest_thenGotAllResult() throws Exception {
        client.get()
            .uri("/api/nearestcity")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(CityAirport.class)
            .hasSize(2);
    }

    @Test
    public void givenRouter_whenGetTest_thenGotSingleResult() throws Exception {
        client.get()
            .uri("/api/nearestcity/Kolkata")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(CityAirport.class)
            .hasSize(1);
    }
}