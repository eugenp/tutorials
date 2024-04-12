package com.baeldung.staticmethods;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsUnitTest {

    @Test
    void givenListOfNumbers_whenReverseStaticMethodIsCalled_thenNumbersInReversedOrderAreReturned() {
        List<String> list = Arrays.asList("1", "2", "3");
        Collections.reverse(list);
        assertThat(list).containsExactly("3", "2", "1");
    }

}
