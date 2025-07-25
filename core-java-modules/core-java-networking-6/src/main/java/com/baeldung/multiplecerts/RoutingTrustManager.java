package com.baeldung.multiplecerts;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

public class RoutingTrustManager extends X509ExtendedTrustManager {

    private final Map<String, X509TrustManager> hostMap = new HashMap<>();

    public void put(String host, X509TrustManager manager) {
        hostMap.put(host, manager);
    }

    private X509TrustManager select(String host) {
        X509TrustManager manager = hostMap.get(host);
        if (manager == null)
            throw new IllegalArgumentException("trust manager not found for " + host);

        return manager;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        String host = host(socket);
        select(host).checkServerTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        String host = engine.getPeerHost();
        select(host).checkServerTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        List<X509Certificate> list = new ArrayList<>();

        hostMap.forEach((host, km) -> list.addAll(Arrays.asList(km.getAcceptedIssuers())));
        return list.toArray(new X509Certificate[] {});
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        String host = host(socket);
        select(host).checkClientTrusted(chain, authType);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        String host = engine.getPeerHost();
        select(host).checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        throw new UnsupportedOperationException("socket is required");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        throw new UnsupportedOperationException("socket is required");
    }

    private String host(Socket socket) {
        if (socket instanceof SSLSocket sslSocket) {
            SSLSession session = sslSocket.getHandshakeSession();
            return session != null ? session.getPeerHost() : null;
        }
        return null;
    }
}
