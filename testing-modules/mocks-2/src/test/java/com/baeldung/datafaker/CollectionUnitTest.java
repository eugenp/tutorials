package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CollectionUnitTest {

    @Test
    void whenGettingFictionaCharacters_thenResultNotEmptyAndOfCorrectSize() {
        assertAll(
                () -> assertThat(Collection.getFictionalCharacters()).isNotEmpty(),
                () -> assertThat(Collection.getFictionalCharacters()).size().isGreaterThanOrEqualTo(Collection.MIN),
                () -> assertThat(Collection.getFictionalCharacters()).size().isLessThanOrEqualTo(Collection.MAX)
        );

    }
}