package com.baeldung.multiplecerts;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;

public class RoutingKeyManager extends X509ExtendedKeyManager {

    private final Map<String, X509KeyManager> hostMap = new HashMap<>();

    public void put(String host, X509KeyManager manager) {
        hostMap.put(host, manager);
    }

    private X509KeyManager select(String host) {
        X509KeyManager manager = hostMap.get(host);
        if (manager == null)
            throw new IllegalArgumentException("key manager not found for " + host);

        return manager;
    }

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        if (socket instanceof SSLSocket sslSocket) {
            String host = host(socket, sslSocket);
            return select(host).chooseClientAlias(keyType, issuers, socket);
        }

        throw new UnsupportedOperationException("unsupported socket");
    }

    @Override
    public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
        String host = engine.getPeerHost();
        return select(host).chooseClientAlias(keyType, issuers, (Socket) null);
    }

    @Override
    public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
        String host = engine.getPeerHost();
        return select(host).chooseServerAlias(keyType, issuers, (Socket) null);
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return select(alias).getCertificateChain(alias);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return select(alias).getPrivateKey(alias);
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        List<String> aliases = new ArrayList<>();

        hostMap.forEach((host, km) -> aliases.addAll(Arrays.asList(km.getClientAliases(keyType, issuers))));
        return aliases.toArray(new String[] {});
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        List<String> list = new ArrayList<>();

        hostMap.forEach((host, km) -> list.addAll(Arrays.asList(km.getServerAliases(keyType, issuers))));
        return list.toArray(new String[] {});
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        if (socket instanceof SSLSocket sslSocket) {
            String host = host(socket, sslSocket);
            return select(host).chooseServerAlias(keyType, issuers, socket);
        }

        throw new UnsupportedOperationException("unsupported socket");
    }

    private String host(Socket socket, SSLSocket sslSocket) {
        SSLSession session = sslSocket.getHandshakeSession();
        return session != null ? session.getPeerHost()
            : socket.getInetAddress()
                .getHostName();
    }
}