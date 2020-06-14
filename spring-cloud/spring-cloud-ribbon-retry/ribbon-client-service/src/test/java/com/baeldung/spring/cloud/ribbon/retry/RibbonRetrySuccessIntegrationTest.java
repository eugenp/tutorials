package com.baeldung.spring.cloud.ribbon.retry;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RibbonClientApp.class)
public class RibbonRetrySuccessIntegrationTest {

    private static ConfigurableApplicationContext weatherServiceInstance1;
    private static ConfigurableApplicationContext weatherServiceInstance2;

    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    public static void setup() {
        weatherServiceInstance1 = startApp(8081);
        weatherServiceInstance2 = startApp(8082);
    }

    private static ConfigurableApplicationContext startApp(int port) {
        return SpringApplication.run(RibbonWeatherServiceApp.class, "--server.port=" + port, "--successful.call.divisor=3");
    }

    @AfterAll
    public static void cleanup() {
        weatherServiceInstance1.close();
        weatherServiceInstance2.close();
    }

    @Test
    public void whenRibbonClientIsCalledAndServiceAvailable_thenSuccess() {
        String url = "http://localhost:" + port + "/client/weather";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody(), "Weather Service Response: Today's a sunny day");
    }

}
