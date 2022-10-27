package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MixedCollectionUnitTest {

    @Test
    void whenGettingMixedCollection_thenResultNotEmptyAndOfCorrectSize() {
        assertAll(
                () -> assertThat(MixedCollection.getMixedCollection()).isNotEmpty(),
                () -> assertThat(MixedCollection.getMixedCollection()).size().isGreaterThanOrEqualTo(MixedCollection.MIN),
                () -> assertThat(MixedCollection.getMixedCollection()).size().isLessThanOrEqualTo(MixedCollection.MAX)
        );
    }
}