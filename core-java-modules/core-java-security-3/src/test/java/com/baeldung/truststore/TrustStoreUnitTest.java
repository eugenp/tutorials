package com.baeldung.truststore;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;

public class TrustStoreUnitTest {

    @Test
    public void whenOpeningTrustStore_thenExceptionIsThrown() throws Exception {
        KeyStore keyStore = getKeyStore();
        InvalidAlgorithmParameterException invalidAlgorithmParameterException =
          Assertions.assertThrows(InvalidAlgorithmParameterException.class, () -> new PKIXParameters(keyStore));
        Assertions.assertEquals("the trustAnchors parameter must be non-empty", invalidAlgorithmParameterException.getMessage());
    }

    private KeyStore getKeyStore() throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, "changeIt".toCharArray());
        return ks;
    }
}