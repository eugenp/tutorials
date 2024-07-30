package com.baeldung.manifold;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComposedUnitTest {
    @Test
    void testAllOf() {
        Composed composed = Composed.builder()
            .withAll(Composed.all.builder()
                .withUsername("testuser")
                .withRoles(List.of("admin"))
                .build())
            .build();

        assertEquals("testuser", composed.getAll().getUsername());
    }

    @Test
    void testAnyOf() {
        Composed composed = Composed.builder()
            .withAnyAscat(Composed.cat.builder()
                .withType(Composed.cat.type.cat)
                .withColor("ginger")
                .build())
            .build();

        assertEquals("ginger", composed.getAnyAscat().getColor());
    }
}
