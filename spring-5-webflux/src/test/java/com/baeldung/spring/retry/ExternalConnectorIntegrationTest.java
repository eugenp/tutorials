package com.baeldung.spring.retry;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.stream.IntStream;

import static io.netty.handler.codec.http.HttpResponseStatus.SERVICE_UNAVAILABLE;
import static io.netty.handler.codec.http.HttpResponseStatus.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

class ExternalConnectorIntegrationTest {

    private ExternalConnector externalConnector;

    private MockWebServer mockExternalService;

    @BeforeEach
    void setup() throws IOException {
       externalConnector = new ExternalConnector(
               WebClient.builder().baseUrl("http://localhost:8090").build()
       );
       mockExternalService = new MockWebServer();
       mockExternalService.start(8090);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockExternalService.shutdown();
    }

    @Test
    void givenExternalServiceReturnsError_whenGettingData_thenRetryAndReturnResponse() {

        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setBody("stock data"));

        StepVerifier.create(externalConnector.getData("ABC"))
                .expectNextMatches(response -> response.equals("stock data"))
                .verifyComplete();

        verifyNumberOfGetRequests(4);
    }

    @Test
    void givenExternalServiceReturnsClientError_whenGettingData_thenNoRetry() {

        mockExternalService.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.code()));

        StepVerifier.create(externalConnector.getData("ABC"))
                .expectError(WebClientResponseException.class)
                .verify();

        verifyNumberOfGetRequests(1);
    }

    @Test
    void givenExternalServiceRetryAttemptsExhausted_whenGettingData_thenRetryAndReturnError() {

        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));

        StepVerifier.create(externalConnector.getData("ABC"))
                .expectError(ServiceException.class)
                .verify();

        verifyNumberOfGetRequests(4);
    }

    private void verifyNumberOfGetRequests(int times) {
        IntStream.range(0, times).forEach(i -> {
            RecordedRequest recordedRequest = getRecordedRequest();
            assertThat(recordedRequest.getMethod()).isEqualTo("GET");
            assertThat(recordedRequest.getPath()).isEqualTo("/data/ABC");
        });
    }

    private RecordedRequest getRecordedRequest() {
        try {
            return mockExternalService.takeRequest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
