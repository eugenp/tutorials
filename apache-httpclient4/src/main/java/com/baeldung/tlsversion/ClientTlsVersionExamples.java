package com.baeldung.tlsversion;

import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ClientTlsVersionExamples {

    public static CloseableHttpClient setViaSocketFactory() {
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
          SSLContexts.createDefault(),
          new String[] { "TLSv1.2", "TLSv1.3" },
          null,
          SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    public static CloseableHttpClient setTlsVersionPerConnection() {
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContexts.createDefault()) {

            @Override
            protected void prepareSocket(SSLSocket socket) {
                String hostname = socket.getInetAddress().getHostName();
                if (hostname.endsWith("internal.system.com")) {
                    socket.setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3" });
                } else {
                    socket.setEnabledProtocols(new String[] { "TLSv1.3" });
                }
            }
        };

        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    // To configure the TLS versions for the client, set the https.protocols system property during runtime.
    // For example: java -Dhttps.protocols=TLSv1.1,TLSv1.2,TLSv1.3 -jar webClient.jar
    public static CloseableHttpClient setViaSystemProperties() {
        return HttpClients.createSystem();
        // Alternatively:
        // return HttpClients.custom().useSystemProperties().build();
    }

    public static void main(String[] args) throws IOException {
        // Alternatively:
        // CloseableHttpClient httpClient = setTlsVersionPerConnection();
        // CloseableHttpClient httpClient = setViaSystemProperties();
        try (CloseableHttpClient httpClient = setViaSocketFactory();
           CloseableHttpResponse response = httpClient.execute(new HttpGet("https://httpbin.org/"))) {

            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        }
    }
}