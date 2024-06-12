package com.baeldung.privatekeyreader;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class PrivateKeyReaderUnitTest {

    @Test
    public void givenKeyStore_whenListPrivateKeys_thenSuccess() throws Exception {
        try (InputStream is = PrivateKeyReaderUnitTest.class.getClassLoader().getResourceAsStream("mykeystore.jks")) {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] passwordCharArr = "mypassword".toCharArray();
            keystore.load(is, passwordCharArr);

            boolean foundPrivateKey = false;
            for (String alias : Collections.list(keystore.aliases())) {
                if (keystore.isKeyEntry(alias)) {
                    KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keystore.getEntry(alias, new KeyStore.PasswordProtection(passwordCharArr));
                    PrivateKey privateKey = pkEntry.getPrivateKey();
                    assertNotNull(privateKey);
                    foundPrivateKey = true;
                }
            }
            assertTrue(foundPrivateKey, "No private keys found in the keystore");
        }
    }

}
