package com.baeldung.javafeatures.mlkemdsa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModuleLatticeBasedKeyEncapsulationUnitTest {

    @Test
    void givenMLKEM_whenSharingSecretKeys_thenSharedSecretKeysMatchedSuccessfully() throws Exception {
        assertTrue(ModuleLatticeBasedKeyEncapsulation.performKeyExchange());
    }
}