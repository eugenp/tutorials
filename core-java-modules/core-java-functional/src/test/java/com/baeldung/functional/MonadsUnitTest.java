package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class MonadsUnitTest {

    @Test
    public void testOptionalAdd() {

        assertEquals(5, Monads.add(Optional.of(new Integer(2)), Optional.of(new Integer(3)))
            .get());

    }

}
