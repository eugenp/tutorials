package com.baeldung.okhttp.events;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class EventTimer extends EventListener {

    private long start;

    private void logTimedEvent(String name) {
        long now = System.nanoTime();
        if (name.equals("callStart")) {
            start = now;
        }
        long elapsedNanos = now - start;
        System.out.printf("%.3f %s%n", elapsedNanos / 1000000000d, name);
    }

    @Override
    public void callStart(Call call) {
        logTimedEvent("callStart");
    }

    @Override
    public void proxySelectStart(Call call, HttpUrl url) {
        logTimedEvent("proxySelectStart");
    }

    @Override
    public void proxySelectEnd(Call call, HttpUrl url, List<Proxy> proxies) {
        logTimedEvent("proxySelectEnd");
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        logTimedEvent("dnsStart");
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        logTimedEvent("dnsEnd");
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        logTimedEvent("connectStart");
    }

    @Override
    public void secureConnectStart(Call call) {
        logTimedEvent("secureConnectStart");
    }

    @Override
    public void secureConnectEnd(Call call, Handshake handshake) {
        logTimedEvent("secureConnectEnd");
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        logTimedEvent("connectEnd");
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
        logTimedEvent("connectFailed");
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        logTimedEvent("connectionAcquired");
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        logTimedEvent("connectionReleased");
    }

    @Override
    public void requestHeadersStart(Call call) {
        logTimedEvent("requestHeadersStart");
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        logTimedEvent("requestHeadersEnd");
    }

    @Override
    public void requestBodyStart(Call call) {
        logTimedEvent("requestBodyStart");
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        logTimedEvent("requestBodyEnd");
    }

    @Override
    public void requestFailed(Call call, IOException ioe) {
        logTimedEvent("requestFailed");
    }

    @Override
    public void responseHeadersStart(Call call) {
        logTimedEvent("responseHeadersStart");
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        logTimedEvent("responseHeadersEnd");
    }

    @Override
    public void responseBodyStart(Call call) {
        logTimedEvent("responseBodyStart");
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        logTimedEvent("responseBodyEnd");
    }

    @Override
    public void responseFailed(Call call, IOException ioe) {
        logTimedEvent("responseFailed");
    }

    @Override
    public void callEnd(Call call) {
        logTimedEvent("callEnd");
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        logTimedEvent("callFailed");
    }

    @Override
    public void canceled(Call call) {
        logTimedEvent("canceled");
    }

}
