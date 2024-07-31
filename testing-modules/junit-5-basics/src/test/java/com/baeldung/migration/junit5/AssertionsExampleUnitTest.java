package com.baeldung.migration.junit5;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AssertionsExampleUnitTest {

    @Test
    @Disabled
    void shouldFailBecauseTheNumbersAreNotEqual() {
        Assertions.assertEquals(2, 3, "Numbers are not equal!");
    }

    @Test
    @Disabled
    void shouldFailBecauseItsNotTrue_overloading() {
        Assertions.assertTrue(() -> {
            return false;
        }, () -> "It's not true!");
    }

    @Test
    void shouldAssertAllTheGroup() {
        List<Integer> list = Arrays.asList(1, 2, 3);

        Assertions.assertAll("List is not incremental",
          () -> Assertions.assertEquals(list.get(0).intValue(), 1),
          () -> Assertions.assertEquals(list.get(1).intValue(), 2),
          () -> Assertions.assertEquals(list.get(2).intValue(), 3));
    }
}
