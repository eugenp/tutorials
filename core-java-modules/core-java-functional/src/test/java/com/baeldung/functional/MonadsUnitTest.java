package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class MonadsUnitTest {

    @Test
    public void testOptionalAdd() {

        assertEquals(Integer.valueOf(5),
            Monads.add(Optional.of(2), Optional.of(3)).get());

    }

}
