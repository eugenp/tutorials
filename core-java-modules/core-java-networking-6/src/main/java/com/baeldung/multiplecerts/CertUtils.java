package com.baeldung.multiplecerts;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.function.Predicate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public class CertUtils {

    private CertUtils() {
    }

    private static KeyStore loadKeyStore(Path path, String password) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore store = KeyStore.getInstance(path.toFile(), password.toCharArray());
        try (InputStream stream = Files.newInputStream(path)) {
            store.load(stream, password.toCharArray());
        }
        return store;
    }

    private static <M> M filter(M[] arr, Predicate<M> predicate) {
        for (M manager : arr) {
            if (predicate.test(manager)) {
                return manager;
            }
        }

        throw new IllegalStateException("no appropriate manager found");
    }

    public static X509KeyManager loadKeyManager(Path path, String password) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {
        KeyStore store = loadKeyStore(path, password);

        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(store, password.toCharArray());

        return (X509KeyManager) filter(factory.getKeyManagers(), X509KeyManager.class::isInstance);
    }

    public static X509TrustManager loadTrustManager(Path path, String password) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
        KeyStore store = loadKeyStore(path, password);

        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(store);

        return (X509TrustManager) filter(factory.getTrustManagers(), X509TrustManager.class::isInstance);
    }
}
