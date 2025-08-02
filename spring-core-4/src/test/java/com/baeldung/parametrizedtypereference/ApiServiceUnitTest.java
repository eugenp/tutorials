package com.baeldung.parametrizedtypereference;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

class ApiServiceUnitTest {

    private WireMockServer wireMockServer;
    private ApiService apiService;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);

        baseUrl = "http://localhost:8089";
        apiService = new ApiService(new RestTemplate(), baseUrl);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void whenFetchingUserList_thenReturnsCorrectType() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/api/users")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                [
                    {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"},
                    {"id": 2, "name": "Jane Smith", "email": "jane@example.com", "department": "Marketing"}
                ]
                """)));

        // when
        List<User> result = apiService.fetchUserList();

        // then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0)
            .getName());
        assertEquals("jane@example.com", result.get(1)
            .getEmail());
        assertEquals("Engineering", result.get(0)
            .getDepartment());
        assertEquals("Marketing", result.get(1)
            .getDepartment());
    }

    @Test
    void whenFetchingUsersCorrectApproach_thenReturnsTypedList() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/api/users")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                [
                    {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"}
                ]
                """)));

        // when
        List<User> result = apiService.fetchUsersCorrectApproach();

        // then
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0)
            .getName());
        assertEquals("Engineering", result.get(0)
            .getDepartment());
    }

    @Test
    void whenFetchingSingleUser_thenReturnsUser() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/api/users/1")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"}
            """)));

        // when
        User result = apiService.fetchUser(1L);

        // then
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("Engineering", result.getDepartment());
    }

    @Test
    void whenFetchingUsersArray_thenReturnsArray() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/api/users")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                [
                    {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"},
                    {"id": 2, "name": "Jane Smith", "email": "jane@example.com", "department": "Marketing"}
                ]
            """)));

        // when
        User[] result = apiService.fetchUsersArray();

        // then
        assertEquals(2, result.length);
        assertEquals("John Doe", result[0].getName());
        assertEquals("Jane Smith", result[1].getName());
    }

    @Test
    void whenFetchingUsersList_thenReturnsTypedList() {
        // given
        wireMockServer.stubFor(get(urlEqualTo("/api/users")).willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""
                [
                    {"id": 1, "name": "John Doe", "email": "john@example.com", "department": "Engineering"},
                    {"id": 2, "name": "Jane Smith", "email": "jane@example.com", "department": "Marketing"}
                ]
                """)));

        // when
        List<User> result = apiService.fetchUsersList();

        // then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0)
            .getName());
        assertEquals("Jane Smith", result.get(1)
            .getName());

        // Verify that we actually get a typed List<User>, not List<Object>
        // This test would fail with ClassCastException if ParameterizedTypeReference wasn't working
        User firstUser = result.get(0);
        assertEquals(Long.valueOf(1L), firstUser.getId());
    }
}