package com.baeldung.basicauth;

import org.junit.jupiter.api.Test;
import java.util.Base64;
import static org.assertj.core.api.Assertions.assertThat;

class BasicAuthExtractorUnitTest {

    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    @Test
    void givenValidHeader_whenExtract_thenReturnCredentialsArray() {
        // Given
        String header = encodeCredentials("admin", "secret");

        // When
        String[] credentials = BasicAuthExtractor.extractCredentials(header);

        // Then
        assertThat(credentials).isNotNull();
        assertThat(credentials).hasSize(2);
        assertThat(credentials[0]).isEqualTo("admin");
        assertThat(credentials[1]).isEqualTo("secret");
    }

    @Test
    void givenMissingBasicPrefix_whenExtract_thenReturnNull() {
        // Given
        String header = "Bearer some-token";

        // When
        String[] credentials = BasicAuthExtractor.extractCredentials(header);

        // Then
        assertThat(credentials).isNull();
    }

}