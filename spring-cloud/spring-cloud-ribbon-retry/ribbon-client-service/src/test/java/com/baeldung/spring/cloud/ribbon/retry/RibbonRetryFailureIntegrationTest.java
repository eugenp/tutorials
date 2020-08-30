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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RibbonClientApp.class)
public class RibbonRetryFailureIntegrationTest {

    private static ConfigurableApplicationContext weatherServiceInstance1;
    private static ConfigurableApplicationContext weatherServiceInstance2;

    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    public static void setup() {
        weatherServiceInstance1 = startApp(8021);
        weatherServiceInstance2 = startApp(8022);
    }

    @AfterAll
    public static void cleanup() {
        weatherServiceInstance1.close();
        weatherServiceInstance2.close();
    }

    private static ConfigurableApplicationContext startApp(int port) {
        return SpringApplication.run(RibbonWeatherServiceApp.class, "--server.port=" + port, "--successful.call.divisor=6");
    }

    @Test
    public void whenRibbonClientIsCalledAndServiceUnavailable_thenFailure() {
        String url = "http://localhost:" + port + "/client/weather";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertTrue(response.getStatusCode().is5xxServerError());
    }

}
