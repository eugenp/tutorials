package com.baeldung.lombok;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BuilderPersonTest {

    @Test
    public void givenPerson_whenUsingBuilder_thenBuildObj() {
        BuilderPerson user = BuilderPerson.builder()
            .id(1L)
            .username("john_doe")
            .password("securePassword")
            .build();

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("securePassword", user.getPassword());
    }
}