package com.baeldung.tlsversion;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;

public class ClientTlsVersionExamples {
    public static CloseableHttpClient setViaSocketFactory() {
        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
            .setDefaultTlsConfig(TlsConfig.custom()
                .setHandshakeTimeout(Timeout.ofSeconds(30))
                .setSupportedProtocols(TLS.V_1_2, TLS.V_1_3)
                .build())
            .build();

        return HttpClients.custom()
            .setConnectionManager(cm)
            .build();
    }

    public static CloseableHttpClient setTlsVersionPerConnection() {
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContexts.createDefault()) {
            @Override
            protected void prepareSocket(SSLSocket socket) {
                String hostname = socket.getInetAddress()
                    .getHostName();
                if (hostname.endsWith("internal.system.com")) {
                    socket.setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3" });
                } else {
                    socket.setEnabledProtocols(new String[] { "TLSv1.3" });
                }
            }
        };

        HttpClientConnectionManager connManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslsf)
            .build();

        return HttpClients.custom()
            .setConnectionManager(connManager)
            .build();

    }

    // To configure the TLS versions for the client, set the https.protocols system property during runtime.
    // For example: java -Dhttps.protocols=TLSv1.1,TLSv1.2,TLSv1.3 -jar webClient.jar
    public static CloseableHttpClient setViaSystemProperties() {
        return HttpClients.createSystem();
        // Alternatively:
        //return HttpClients.custom().useSystemProperties().build();
    }

    public static void main(String[] args) throws IOException {
        try (CloseableHttpClient httpClient = setViaSocketFactory(); CloseableHttpResponse response = httpClient.execute(new HttpGet("https://httpbin.org/"))) {

            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        }
    }
}