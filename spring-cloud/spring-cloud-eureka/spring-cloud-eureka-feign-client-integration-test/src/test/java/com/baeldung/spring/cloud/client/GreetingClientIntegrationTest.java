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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.Assert.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
class GreetingClientIntegrationTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private BooksClient booksClient;

    @BeforeEach
    void setUp() throws IOException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/books"))
          .willReturn(WireMock.aResponse()
            .withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(StreamUtils.copyToString(getClass().getClassLoader().getResourceAsStream("payload/get-books-response.json"), Charset.defaultCharset()))));
    }

    @Test
    public void whenGetBooks_thenListBooksSizeGreaterThanZero() {
        List<Book> books = booksClient.getBooks();

        assertFalse(books.isEmpty());
    }

}