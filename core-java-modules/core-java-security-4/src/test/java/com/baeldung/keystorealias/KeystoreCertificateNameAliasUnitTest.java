package com.baeldung.keystorealias;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import org.junit.jupiter.api.Test;

public class KeystoreCertificateNameAliasUnitTest {
    private static final String KEYSTORE_FILE = "my-keystore.jks";
    private static final String KEYSTORE_PWD = "storepw@1";
    private static final String KEYSTORE_ALIAS = "baeldung";

    private KeyStore readKeyStore() throws Exception {
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(getClass().getResourceAsStream(KEYSTORE_FILE), KEYSTORE_PWD.toCharArray());
        return keystore;
    }

    @Test
    void whenCheckingAliasAndName_thenMatchIsFound() throws Exception {
        KeyStore keystore = readKeyStore();

        assertThat(keystore.containsAlias(KEYSTORE_ALIAS)).isTrue();

        X509Certificate x509Certificate = (X509Certificate) keystore.getCertificate(KEYSTORE_ALIAS);
        String ownerName = x509Certificate.getSubjectX500Principal().getName();
        assertThat(ownerName.contains("my-cn.localhost")).isTrue();
    }

    @Test
    void whenCheckingAliasAndName_thenNameIsNotFound() throws Exception {
        KeyStore keystore = readKeyStore();

        assertThat(keystore.containsAlias(KEYSTORE_ALIAS)).isTrue();

        X509Certificate x509Certificate = (X509Certificate) keystore.getCertificate(KEYSTORE_ALIAS);
        String ownerName = x509Certificate.getSubjectX500Principal().getName();
        assertThat(ownerName.contains("commonName1")).isFalse();
    }

    @Test
    void whenCheckingAliasAndName_thenAliasIsNotFound() throws Exception {
        KeyStore keystore = readKeyStore();

        assertThat(keystore.containsAlias("alias1")).isFalse();
    }
}