package com.baeldung.manifold;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ComplexUnitTest {
    @Test
    void whenRenderingComplex_thenCorrectJsonIsProduced() {
        Complex complex = Complex.builder( "writeonly")
            .withRo("readonly")
            .build();
        complex.setDt(LocalDateTime.parse("2024-07-08T12:34:56"));
        complex.put("other", "value");

        assertEquals("""
            {
              "wo": "writeonly",
              "ro": "readonly",
              "dt": "2024-07-08T12:34:56",
              "other": "value"
            }""", complex.write().toJson());
    }
}
