package com.baeldung.manifold;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexUserUnitTest {
    @Test
    void whenRenderingComplexUser_thenCorrectJsonIsProduced() {
        ComplexUser user = ComplexUser.builder("testuser", "Test User")
            .withEmail(ComplexUser.email.builder("testuser@example.com", false).build())
            .build();
        assertEquals("""
            {
              "username": "testuser",
              "name": "Test User",
              "email": {
                "address": "testuser@example.com",
                "verified": false
              }
            }""", user.write().toJson());
    }
}
