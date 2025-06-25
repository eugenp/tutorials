package com.baeldung.multiplecerts;

import java.io.IOException;
import java.nio.file.Path;
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

    public RoutingSslContextBuilder trust(String host, Path certificate, String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        routingKeyManager.put(host, CertUtils.loadKeyManager(certificate, password));
        routingTrustManager.put(host, CertUtils.loadTrustManager(certificate, password));
        return this;
    }

    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(new KeyManager[] { routingKeyManager }, new TrustManager[] { routingTrustManager }, null);

        return context;
    }
}
