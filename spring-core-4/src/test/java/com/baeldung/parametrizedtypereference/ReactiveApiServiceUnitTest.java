package com.baeldung.parametrizedtypereference;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ReactiveApiServiceUnitTest {

    private WireMockServer wireMockServer;
    private ReactiveApiService reactiveApiService;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8090);

        baseUrl = "http://localhost:8090";
        reactiveApiService = new ReactiveApiService(baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void whenFetchingUsersByDepartment_thenReturnsCorrectMap() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/users/by-department")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {
                    "Engineering": [
                        {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"}
                    ],
                    "Marketing": [
                        {"id": 2, "name": "Jane Smith", "email": "jane@example.com", "department": "Marketing"}
                    ]
                }
                """)));

        // when
        Mono<Map<String, List<User>>> result = reactiveApiService.fetchUsersByDepartment();

        // then
        StepVerifier.create(result)
            .assertNext(map -> {
                assertTrue(map.containsKey("Engineering"));
                assertTrue(map.containsKey("Marketing"));
                assertEquals("John Doe", map.get("Engineering")
                    .get(0)
                    .getName());
                assertEquals("Jane Smith", map.get("Marketing")
                    .get(0)
                    .getName());

                // Verify proper typing - this would fail if ParameterizedTypeReference didn't work
                List<User> engineeringUsers = map.get("Engineering");
                User firstUser = engineeringUsers.get(0);
                assertEquals(Long.valueOf(1L), firstUser.getId());
            })
            .verifyComplete();
    }

    @Test
    void whenFetchingUsersWithWrapper_thenReturnsApiResponse() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/users/wrapped")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {
                    "success": true,
                    "message": "Success",
                    "data": [
                        {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"},
                        {"id": 2, "name": "Jane Smith", "email": "jane@example.com", "department": "Marketing"}
                    ]
                }
                """)));

        // when
        Mono<ApiResponse<List<User>>> result = reactiveApiService.fetchUsersWithWrapper();

        // then
        StepVerifier.create(result)
            .assertNext(response -> {
                assertTrue(response.success());
                assertEquals("Success", response.message());
                assertEquals(2, response.data()
                    .size());
                assertEquals("John Doe", response.data()
                    .get(0)
                    .getName());
                assertEquals("Jane Smith", response.data()
                    .get(1)
                    .getName());

                // Verify proper generic typing - this ensures ParameterizedTypeReference worked
                List<User> users = response.data();
                User firstUser = users.get(0);
                assertEquals(Long.valueOf(1L), firstUser.getId());
                assertEquals("Engineering", firstUser.getDepartment());
            })
            .verifyComplete();
    }

    @Test
    void whenApiReturnsError_thenHandlesGracefully() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/users/by-department")).willReturn(aResponse().withStatus(500)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {"error": "Internal server error"}
                """)));

        // when
        Mono<Map<String, List<User>>> result = reactiveApiService.fetchUsersByDepartment();

        // then
        StepVerifier.create(result)
            .expectError()
            .verify();
    }

    @Test
    void whenEmptyResponse_thenHandlesCorrectly() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/users/by-department")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{}")));

        // when
        Mono<Map<String, List<User>>> result = reactiveApiService.fetchUsersByDepartment();

        // then
        StepVerifier.create(result)
            .assertNext(map -> {
                assertTrue(map.isEmpty());
            })
            .verifyComplete();
    }
}