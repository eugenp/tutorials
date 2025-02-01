package com.baeldung.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class RetrieveUuidControllerUnitTest {

    @Autowired
    RetrieveUuidController retrieveUuidController;

    @Test
    void whenGetUuid_thenReturnValidUuid() {
        ResponseEntity<String> response = retrieveUuidController.uuid();

        assertNotNull(response);
        assertNotNull(response.getBody());
        String responseBody = response.getBody();
        String uuidCandidate = responseBody.substring(16, responseBody.length() - 1);
        assertDoesNotThrow(() -> UUID.fromString(uuidCandidate));
        assertEquals(uuidCandidate, UUID.fromString(uuidCandidate).toString());
    }
}
