package com.baeldung.examples.copying;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrimitiveCopyingUnitTest {

    @Test
    void givenSourcePrimitive_whenCopyIt_thenExpectSameValue() {
        //arrange
        int source = RandomUtils.nextInt();

        //act
        int target = new PrimitiveCopying().copy(source);

        //assert
        assertThat(target)
                .withFailMessage("Target primitive value should be equals to the source")
                .isEqualTo(source);
    }

}