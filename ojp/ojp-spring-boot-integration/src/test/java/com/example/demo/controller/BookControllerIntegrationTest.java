package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.testcontainers.OjpContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class BookControllerIntegrationTest {

    // Create a shared network for the containers
    static Network network = Network.newNetwork();

    // Start OJP container first
    @Container
    static OjpContainer ojpContainer = new OjpContainer()
            .withNetwork(network)
            .withNetworkAliases("ojp");

    // Start PostgreSQL container after OJP
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withNetwork(network)
            .withNetworkAliases("postgres")
            .withCommand("postgres", "-c", "max_prepared_transactions=100");

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testCreateAndReadBook() throws Exception {
        String baseUrl = "http://localhost:" + port;
        
        // Create a new book
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        // Test Create operation (POST /books)
        ResponseEntity<Book> createResponse = restTemplate.postForEntity(
                baseUrl + "/books", 
                book, 
                Book.class
        );
        
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getTitle()).isEqualTo("Test Book");
        assertThat(createResponse.getBody().getAuthor()).isEqualTo("Test Author");
        assertThat(createResponse.getBody().getId()).isNotNull();

        // Test Read operation (GET /books)
        ResponseEntity<List<Book>> readResponse = restTemplate.exchange(
                baseUrl + "/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}
        );
        
        assertThat(readResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(readResponse.getBody()).isNotNull();
        assertThat(readResponse.getBody()).hasSize(1);
        assertThat(readResponse.getBody().get(0).getTitle()).isEqualTo("Test Book");
        assertThat(readResponse.getBody().get(0).getAuthor()).isEqualTo("Test Author");
        assertThat(readResponse.getBody().get(0).getId()).isNotNull();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Build the OJP JDBC URL format: jdbc:ojp[ojp_host:ojp_port]_postgresql://postgres_host:postgres_port/database
        // Since containers are on the same network, we use the network alias for communication within the network
        // But the application runs on the host, so it needs to use the exposed ports
        String postgresHostOnNetwork = "postgres";
        String postgresPortOnNetwork = "5432";
        String postgresDatabase = postgres.getDatabaseName();
        
        // OJP connection from host
        String ojpHost = ojpContainer.getHost();
        Integer ojpPort = ojpContainer.getOjpPort();
        
        // The OJP JDBC URL format that the application (running on host) will use
        // The OJP container needs to know how to reach PostgreSQL on the network
        String ojpJdbcUrl = String.format(
            "jdbc:ojp[%s:%d]_postgresql://%s:%s/%s",
            ojpHost,
            ojpPort,
            postgresHostOnNetwork,
            postgresPortOnNetwork,
            postgresDatabase
        );
        
        registry.add("spring.datasource.url", () -> ojpJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.openjproxy.jdbc.Driver");
    }
}
