package com.baeldung.java9;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OptionalToStreamUnitTest {

    @Test
    public void testOptionalToStream() {
        Optional<String> op = Optional.ofNullable("String value");
        Stream<String> strOptionalStream = op.stream();
        Stream<String> filteredStream = strOptionalStream.filter((str) -> {
            return str != null && str.startsWith("String");
        });
        assertEquals(1, filteredStream.count());

    }
}
