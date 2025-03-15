package com.baeldung.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootTest(classes = SimpleWiremockTest.AppConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
  @ConfigureWireMock(name = "user-service", port = 8081),
  @ConfigureWireMock(name = "product-service", port = 8082)
})
public class InjectedWiremockTest {

    @InjectWireMock("user-service")
    WireMockServer mockUserService;

    @InjectWireMock("product-service")
    WireMockServer mockProductService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenEmptyUserList_whenFetchingUsers_thenReturnsEmptyList() {
        mockUserService.stubFor(get("/users").willReturn(okJson("[]")));

        ResponseEntity<String> response = restTemplate
          .getForEntity("http://localhost:8081/users", String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("[]", response.getBody());
    }

    @Test
    void givenUserAndProductLists_whenFetchingUsersAndProducts_thenReturnsMockedData() {
        mockUserService
          .stubFor(get("/users")
            .willReturn(okJson("[{\"id\": 1, \"name\": \"John\"}]")));
        mockProductService
          .stubFor(get("/products")
            .willReturn(okJson("[{\"id\": 101, \"name\": \"Laptop\"}]")));

        ResponseEntity<String> userResponse = restTemplate
          .getForEntity("http://localhost:8081/users", String.class);
        ResponseEntity<String> productResponse = restTemplate
          .getForEntity("http://localhost:8082/products", String.class);

        Assertions.assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Assertions.assertEquals("[{\"id\": 1, \"name\": \"John\"}]", userResponse.getBody());

        Assertions.assertEquals(HttpStatus.OK, productResponse.getStatusCode());
        Assertions.assertEquals("[{\"id\": 101, \"name\": \"Laptop\"}]", productResponse.getBody());
    }

    @SpringBootApplication
    static class AppConfiguration {}
}
