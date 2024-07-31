package com.baeldung.uricreation;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class UriCreationUnitTest {
    @Test
    void givenValidUriString_whenUsingConstructor_shouldGetExpectedResult() {
        try {
            URI myUri = new URI("https://www.baeldung.com/articles");
            assertNotNull(myUri);
        } catch (URISyntaxException e) {
            fail();
        }
    }

    @Test
    void givenInvalidUriString_whenUsingConstructor_shouldGetExpectedResult() {
        assertThrows(URISyntaxException.class, () -> new URI("I am an invalid URI string."));
    }

    @Test
    void givenValidUriString_whenUsingCreateMethod_shouldGetExpectedResult() {
        URI myUri = URI.create("https://www.baeldung.com/articles");
        assertNotNull(myUri);
    }

    @Test
    void givenInvalidUriString_whenUsingCreateMethod_shouldGetExpectedResult() {
        assertThrows(IllegalArgumentException.class, () -> URI.create("I am an invalid URI string."));
    }
}
