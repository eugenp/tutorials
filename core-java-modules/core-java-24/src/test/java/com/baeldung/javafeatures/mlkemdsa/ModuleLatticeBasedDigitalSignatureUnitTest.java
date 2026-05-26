package com.baeldung.javafeatures.mlkemdsa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModuleLatticeBasedDigitalSignatureUnitTest {

    @Test
    void givenMLDSA_whenSigningMessage_thenSignatureVerifiedSuccessfully() throws Exception {
        assertTrue(ModuleLatticeBasedDigitalSignature.verifyDigitalSignature());
    }
}