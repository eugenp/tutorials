package com.baeldung.architecture.hexagonal.sprigboot.side.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookRestControllerAdapterIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenSendingABookToDonation_thenThisBookShallDisplayInTheSearchByName() throws Exception {
        String testHost = "http://localhost:" + port + "/books";

        BookDto savedBook = restTemplate.postForEntity(testHost, new BookDto("Baeldung", "Hexagonal Architecture"), BookDto.class)
            .getBody();
        Assertions.assertThat(savedBook)
            .isEqualToComparingFieldByField(new BookDto(1L, "Baeldung", "Hexagonal Architecture"));

        BookDto[] foundBook = restTemplate.getForEntity(testHost + "?name=Hexagonal", BookDto[].class)
            .getBody();
        Assertions.assertThat(foundBook[0])
            .isEqualToComparingFieldByField(savedBook);

    }

}
