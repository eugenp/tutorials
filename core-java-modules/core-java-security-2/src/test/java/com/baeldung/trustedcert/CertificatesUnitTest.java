package com.baeldung.trustedcert;

import org.junit.jupiter.api.Test;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CertificatesUnitTest {

    // Aliases differ between JDK vendors (e.g. "godaddyrootg2ca [jdk]" in OpenJDK/Oracle,
    // something else in Temurin or Debian/Ubuntu builds), so we match on the certificate subject instead
    private static final String GODADDY_CA_SUBJECT = "CN=Go Daddy Root Certificate Authority - G2";

    @Test
    public void whenLoadingCacertsKeyStore_thenCertificatesArePresent() throws Exception {
        KeyStore keyStore = loadKeyStore();
        PKIXParameters params = new PKIXParameters(keyStore);

        Set<TrustAnchor> trustAnchors = params.getTrustAnchors();
        List<Certificate> certificates = trustAnchors.stream()
          .map(TrustAnchor::getTrustedCert)
          .collect(Collectors.toList());

        assertFalse(certificates.isEmpty());
    }

    @Test
    public void whenLoadingDefaultKeyStore_thenCertificatesArePresent() throws Exception {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);

        List<TrustManager> trustManagers = Arrays.asList(trustManagerFactory.getTrustManagers());
        List<X509Certificate> certificates = trustManagers.stream()
          .filter(X509TrustManager.class::isInstance)
          .map(X509TrustManager.class::cast)
          .map(trustManager -> Arrays.asList(trustManager.getAcceptedIssuers()))
          .flatMap(Collection::stream)
          .collect(Collectors.toList());

        assertFalse(certificates.isEmpty());
    }

    @Test
    public void whenLoadingKeyStore_thenGoDaddyCALabelIsPresent() throws Exception {
        KeyStore keyStore = loadKeyStore();

        Optional<String> goDaddyAlias = findAliasBySubject(keyStore, GODADDY_CA_SUBJECT);

        assertTrue(goDaddyAlias.isPresent());
        assertTrue(keyStore.containsAlias(goDaddyAlias.get()));
    }

    @Test
    public void whenLoadingKeyStore_thenGoDaddyCertificateIsPresent() throws Exception {
        KeyStore keyStore = loadKeyStore();

        Optional<String> goDaddyAlias = findAliasBySubject(keyStore, GODADDY_CA_SUBJECT);
        assertTrue(goDaddyAlias.isPresent());

        Certificate goDaddyCertificate = keyStore.getCertificate(goDaddyAlias.get());

        assertNotNull(goDaddyCertificate);
    }

    private Optional<String> findAliasBySubject(KeyStore keyStore, String subject) throws KeyStoreException {
        for (String alias : Collections.list(keyStore.aliases())) {
            Certificate certificate = keyStore.getCertificate(alias);
            if (certificate instanceof X509Certificate
              && ((X509Certificate) certificate).getSubjectX500Principal().getName().contains(subject)) {
                return Optional.of(alias);
            }
        }
        return Optional.empty();
    }

    private KeyStore loadKeyStore() throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        String relativeCacertsPath = "/lib/security/cacerts".replace("/", File.separator);
        String filename = System.getProperty("java.home") + relativeCacertsPath;

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        String password = "changeit";
        try (InputStream is = new FileInputStream(filename)) {
            keystore.load(is, password.toCharArray());
        }

        return keystore;
    }
}
