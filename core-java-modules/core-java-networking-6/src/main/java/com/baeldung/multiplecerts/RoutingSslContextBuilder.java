package com.baeldung.multiplecerts;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class RoutingSslContextBuilder {

    private final RoutingKeyManager routingKeyManager;
    private final RoutingTrustManager routingTrustManager;

    public RoutingSslContextBuilder() {
        routingKeyManager = new RoutingKeyManager();
        routingTrustManager = new RoutingTrustManager();
    }

    public static RoutingSslContextBuilder create() {
        return new RoutingSslContextBuilder();
    }

    public RoutingSslContextBuilder trust(String host, String certsDir, String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        routingTrustManager.put(host, CertUtils.loadTrustManager(Paths.get(certsDir, "trust." + host + ".p12"), password));
        routingKeyManager.put(host, CertUtils.loadKeyManager(Paths.get(certsDir, "client." + host + ".p12"), password));
        return this;
    }

    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(new KeyManager[] { routingKeyManager }, new TrustManager[] { routingTrustManager }, null);

        return context;
    }
}
