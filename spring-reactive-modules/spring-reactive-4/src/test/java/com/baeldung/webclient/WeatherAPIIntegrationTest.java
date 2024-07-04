package com.baeldung.webclient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class WeatherAPIIntegrationTest {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${wiremock.server.port}")
    private int wireMockPort;

    @BeforeEach
    void init() {
        WireMock.reset();
    }
    @Test
    public void givenWebClientBaseURLConfiguredToWireMock_whenGetRequestForACity_thenWebClientRecievesSuccessResponse() {
        stubFor(WireMock.get(urlEqualTo("/weather?city=London"))
          .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"city\": \"London\", \"temperature\": 20, \"description\": \"Cloudy\"}")));

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort)
          .build();

        WeatherData weatherData = webClient.get()
          .uri("/weather?city=London")
          .retrieve()
          .bodyToMono(WeatherData.class)
          .block();

        assertNotNull(weatherData);
        assertEquals("London", weatherData.getCity());
        assertEquals(20, weatherData.getTemperature());
        assertEquals("Cloudy", weatherData.getDescription());
    }

    @Test
    public void givenWebClientBaseURLConfiguredToWireMock_whenGetRequestWithInvalidCity_thenExceptionReturnedFromWireMock() {
        stubFor(WireMock.get(urlEqualTo("/weather?city=InvalidCity"))
          .willReturn(aResponse().withStatus(404)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"error\": \"City not found\"}")));

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort)
          .build();

        // Fetch weather data for an invalid city
        WebClientResponseException exception = assertThrows(WebClientResponseException.class, () -> {
            webClient.get()
              .uri("/weather?city=InvalidCity")
              .retrieve()
              .bodyToMono(WeatherData.class)
              .block();
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void givenWebClientBaseURLConfiguredToWireMock_whenGetRequest_thenResponseReturnedWithDelay() {
        // Stubbing response with a delay
        stubFor(WireMock.get(urlEqualTo("/weather?city=London"))
          .willReturn(aResponse().withStatus(200)
            .withFixedDelay(1000) // 1 second delay
            .withHeader("Content-Type", "application/json")
            .withBody("{\"city\": \"London\", \"temperature\": 20, \"description\": \"Cloudy\"}")));

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort)
          .build();

        long startTime = System.currentTimeMillis();
        WeatherData weatherData = webClient.get()
          .uri("/weather?city=London")
          .retrieve()
          .bodyToMono(WeatherData.class)
          .block();
        long endTime = System.currentTimeMillis();

        assertNotNull(weatherData);
        assertTrue(endTime - startTime >= 1000); // Assert the delay
    }

    @Test
    public void givenWebClientBaseURLConfiguredToWireMock_whenGetRequest_theCustomHeaderIsReturned() {
        // Stubbing response with custom headers
        stubFor(WireMock.get(urlEqualTo("/weather?city=London"))
          .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("X-Custom-Header", "foobar")
            .withBody("{\"city\": \"London\", \"temperature\": 20, \"description\": \"Cloudy\"}")));

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort)
          .build();

        // Fetch weather data for London
        WeatherData weatherData = webClient.get()
          .uri("/weather?city=London")
          .retrieve()
          .bodyToMono(WeatherData.class)
          .block();

        assertNotNull(weatherData);
        assertEquals("London", weatherData.getCity());

        // Assert the custom header
        HttpHeaders httpHeaders = webClient.get()
          .uri("/weather?city=London")
          .exchange()
          .block()
          .headers()
          .asHttpHeaders();
        assertEquals("foobar", httpHeaders.getFirst("X-Custom-Header"));
    }

    @Test
    public void givenWebClientBaseURLConfiguredToWireMock_whenGetRequest_theDynamicResponseIsSent() {
        // Generate a random temperature value between 10 and 30
        int temperature = ThreadLocalRandom.current()
          .nextInt(10, 31);

        // Construct the response body with the generated temperature value
        String responseBody =
          "{\"city\": \"London\", \"temperature\": " + temperature + ", \"description\": \"Cloudy\"}";

        // Stubbing response with dynamic temperature
        stubFor(WireMock.get(urlEqualTo("/weather?city=London"))
          .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(responseBody)));

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort)
          .build();

        WeatherData weatherData = webClient.get()
          .uri("/weather?city=London")
          .retrieve()
          .bodyToMono(WeatherData.class)
          .block();

        // Assert temperature is within the expected range
        assertNotNull(weatherData);
        assertTrue(weatherData.getTemperature() >= 10 && weatherData.getTemperature() <= 30);
    }

        @Test
        public void givenWebClientWithBaseURLConfiguredToWireMock_whenGetWithQueryParameter_thenWireMockReturnsResponse() {
            // Stubbing response with specific query parameters
            stubFor(WireMock.get(urlPathEqualTo("/weather"))
              .withQueryParam("city", equalTo("London"))
              .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"city\": \"London\", \"temperature\": 20, \"description\": \"Cloudy\"}")));

            WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort).build();

            WeatherData londonWeatherData = webClient.get()
              .uri(uriBuilder -> uriBuilder.path("/weather").queryParam("city", "London").build())
              .retrieve()
              .bodyToMono(WeatherData.class)
              .block();

            assertNotNull(londonWeatherData);
            assertEquals("London", londonWeatherData.getCity());
        }

    @Test
    public void givenWebClientBaseURLConfiguredToWireMock_whenMulitpleGet_thenWireMockReturnsMultipleResponsesBasedOnState() {
        // Stubbing response for the first call
        stubFor(WireMock.get(urlEqualTo("/weather?city=London"))
          .inScenario("Weather Scenario")
          .whenScenarioStateIs(Scenario.STARTED)
          .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"city\": \"London\", \"temperature\": 20, \"description\": \"Cloudy\"}"))
          .willSetStateTo("Weather Found"));

        // Stubbing response for the second call
        stubFor(WireMock.get(urlEqualTo("/weather?city=London"))
          .inScenario("Weather Scenario")
          .whenScenarioStateIs("Weather Found")
          .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"city\": \"London\", \"temperature\": 25, \"description\": \"Sunny\"}")));

        // Create WebClient instance with WireMock base URL
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockPort)
          .build();

        // Fetch weather data for London
        WeatherData firstWeatherData = webClient.get()
          .uri("/weather?city=London")
          .retrieve()
          .bodyToMono(WeatherData.class)
          .block();

        // Assert the first response
        assertNotNull(firstWeatherData);
        assertEquals("London", firstWeatherData.getCity());
        assertEquals(20, firstWeatherData.getTemperature());
        assertEquals("Cloudy", firstWeatherData.getDescription());

        // Fetch weather data for London again
        WeatherData secondWeatherData = webClient.get()
          .uri("/weather?city=London")
          .retrieve()
          .bodyToMono(WeatherData.class)
          .block();

        // Assert the second response
        assertNotNull(secondWeatherData);
        assertEquals("London", secondWeatherData.getCity());
        assertEquals(25, secondWeatherData.getTemperature());
        assertEquals("Sunny", secondWeatherData.getDescription());
    }
}
