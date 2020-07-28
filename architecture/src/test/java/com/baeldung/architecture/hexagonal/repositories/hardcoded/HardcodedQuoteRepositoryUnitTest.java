package com.baeldung.architecture.hexagonal.repositories.hardcoded;

import java.util.List;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import com.baeldung.architecture.hexagonal.domain.repositories.QuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class HardcodedQuoteRepositoryUnitTest {

    @Autowired
    private QuoteRepository sut;

    @BeforeEach
    void setUp() {
        this.sut = new HardcodedQuoteRepository();
    }

    @Test
    void givenHardcodedQuotes_whenRequestAllQuotes_thenReturnAllQuotes() {
        List<Quote> quotes = this.sut.getAllQuotes();

        assertThat(quotes).hasSize(5);
    }
}
