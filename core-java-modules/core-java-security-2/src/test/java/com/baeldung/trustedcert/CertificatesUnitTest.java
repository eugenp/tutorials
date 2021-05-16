package com.baeldung.trustedcert;

import org.junit.jupiter.api.Test;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CertificatesUnitTest {

    private static final String GODADDY_CA_ALIAS = "godaddyrootg2ca [jdk]";

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
        trustManagerFactory.init((KeyStore)null);

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

        Enumeration<String> aliasEnumeration = keyStore.aliases();
        List<String> aliases = Collections.list(aliasEnumeration);

        assertTrue(aliases.contains(GODADDY_CA_ALIAS));
    }

    @Test
    public void whenLoadingKeyStore_thenGoDaddyCertificateIsPresent() throws Exception {
        KeyStore keyStore = loadKeyStore();

        Certificate goDaddyCertificate = keyStore.getCertificate(GODADDY_CA_ALIAS);

        assertNotNull(goDaddyCertificate);
    }

    private KeyStore loadKeyStore() throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        String relativeCacertsPath = "/lib/security/cacerts".replace("/", File.separator);
        String filename = System.getProperty("java.home") + relativeCacertsPath;
        FileInputStream is = new FileInputStream(filename);

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        String password = "changeit";
        keystore.load(is, password.toCharArray());

        return keystore;
    }
}
