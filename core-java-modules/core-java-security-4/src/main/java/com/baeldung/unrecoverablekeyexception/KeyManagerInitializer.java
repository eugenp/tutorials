package com.baeldung.unrecoverablekeyexception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;

public class KeyManagerInitializer {

  public static X509ExtendedKeyManager initializeKeyManager(String privateKeyPassword, String keystoreLocation)
      throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, URISyntaxException {
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
    InputStream resourceAsStream = Files.newInputStream(Paths.get(ClassLoader.getSystemResource(keystoreLocation).toURI()));
    instance.load(resourceAsStream, "admin123".toCharArray());
    kmf.init(instance, privateKeyPassword.toCharArray());
    return (X509ExtendedKeyManager) kmf.getKeyManagers()[0];
  }

  public static X509ExtendedKeyManager initializeKeyManager(String privateKeyPassword)
      throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, URISyntaxException {
    return initializeKeyManager(privateKeyPassword, "single_entry_keystore.jks");
  }
}
