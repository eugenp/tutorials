package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.model.Book;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static com.baeldung.spring.cloud.client.BookMocks.setupMockBooksResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.moreThan;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("ribbon-test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RibbonTestConfig.class })
class LoadBalancerBooksClientIntegrationTest {

    @Autowired
    private WireMockServer mockBooksService;

    @Autowired
    private WireMockServer secondMockBooksService;

    @Autowired
    private BooksClient booksClient;

    @BeforeEach
    void setUp() throws IOException {
        setupMockBooksResponse(mockBooksService);
        setupMockBooksResponse(secondMockBooksService);
    }

    @Test
    void whenGetBooks_thenRequestsAreLoadBalanced() {
        for (int k = 0; k < 10; k++) {
            booksClient.getBooks();
        }

        mockBooksService.verify(
          moreThan(0), getRequestedFor(WireMock.urlEqualTo("/books")));
        secondMockBooksService.verify(
          moreThan(0), getRequestedFor(WireMock.urlEqualTo("/books")));
    }

    @Test
    public void whenGetBooks_thenTheCorrectBooksShouldBeReturned() {
        assertTrue(booksClient.getBooks()
          .containsAll(asList(
            new Book("Dune", "Frank Herbert"),
            new Book("Foundation", "Isaac Asimov"))));
    }
}
