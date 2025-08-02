package com.baeldung.manifold;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ComposedUnitTest {
    @Test
    void whenConstructingAllOf_thenCorrectTypesAreUsed() {
        Composed composed = Composed.builder()
            .withAll(Composed.all.builder()
                .withUsername("testuser")
                .withRoles(List.of("admin"))
                .build())
            .build();

        assertEquals("testuser", composed.getAll().getUsername());
        assertEquals(List.of("admin"), composed.getAll().getRoles());
    }

    @Test
    void whenConstructingAnyOf_thenCorrectTypesAreUsed() {
        Composed composed = Composed.builder()
            .withAnyAscat(Composed.cat.builder()
                .withType(Composed.cat.type.cat)
                .withColor("ginger")
                .build())
            .build();

        assertEquals("ginger", composed.getAnyAscat().getColor());
    }
}
