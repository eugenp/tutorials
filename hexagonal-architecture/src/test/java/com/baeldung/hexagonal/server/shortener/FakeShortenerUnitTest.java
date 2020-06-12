package com.baeldung.hexagonal.server.shortener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeShortenerUnitTest {

    private FakeShortener shortener;

    @BeforeEach
    public void setUp() {
        shortener = new FakeShortener();
    }

    @ParameterizedTest
    @MethodSource("provideUrls")
    void whenShortenUrl_thenReturnShortenedCodeWithLen8(String url) {
        String code = shortener.shortenUrl(url);
        assertThat(code).hasSize(8);
    }

    static Stream<String> provideUrls() {
        return Stream.of(
                "www.google.com",
                "www.baeldung.com",
                "www.twitter.com",
                ""
        );
    }

}
