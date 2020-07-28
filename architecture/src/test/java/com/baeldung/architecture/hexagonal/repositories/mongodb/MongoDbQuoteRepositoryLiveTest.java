package com.baeldung.architecture.hexagonal.repositories.mongodb;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.repositories.mondodb.MongoDbQuoteRepository;
import com.baeldung.architecture.hexagonal.repositories.mondodb.SpringDataMongoQuoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

/*
 To run this test we need to run the databases first.
 A dedicated docker-compose.yml file is located under the resources directory.
 We can run it by simple executing `docker-compose up`.
 */
@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:hexagonal-architecture.properties")
class MongoDbQuoteRepositoryLiveTest {

    @Autowired
    private SpringDataMongoQuoteRepository springDataMongoQuoteRepository;

    @Autowired
    private MongoDbQuoteRepository sut;

    @AfterEach
    void cleanUp() {
        springDataMongoQuoteRepository.deleteAll();
    }

    @Test
    void givenOneShakespeareQuote_whenRetrievingAllQuotes_ThenShakespeareQuoteIsPresent() {

        List<Quote> shakespeareQuotes = sut.getAllQuotes()
            .stream()
            .filter(q -> q.getAuthor().contains("Shakespeare"))
            .collect(Collectors.toList());

        assertThat(shakespeareQuotes).hasSize(1);
        Quote quote = shakespeareQuotes.get(0);
        assertThat(quote.getAuthor()).isEqualTo("William Shakespeare");
    }

}
