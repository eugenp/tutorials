package com.baeldung.likewithregex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class LikeWithRegexUnitTest {

    @Autowired
    private MessageRepository repository;

    @Test
    void givenContainsDigitRegex_whenLikeRegexp_thenReturnsMatching() {
        List<Message> results = repository.findByContentMatchingRegex(".*\\d+.*");

        assertThat(results)
            .extracting(Message::getContent)
            .containsExactlyInAnyOrder(
                "Order 1234 shipped on Monday",
                "Please call 555 today");
    }

    @Test
    void givenContainsDigitRegex_whenNotLikeRegexp_thenReturnsNonMatching() {
        List<Message> results = repository.findByContentNotMatchingRegex(".*\\d+.*");

        assertThat(results)
            .extracting(Message::getContent)
            .containsExactlyInAnyOrder(
                "quick brown fox jumps over",
                "hello world from spring boot",
                "ERROR connection refused remotely",
                "warning low disk space detected");
    }

}