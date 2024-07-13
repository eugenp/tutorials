package com.baeldung.twilio.whatsapp;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import com.twilio.http.HttpClient;
import com.twilio.http.NetworkHttpClient;
import com.twilio.http.TwilioRestClient;
import com.twilio.http.TwilioRestClient.Builder;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
class TwilioProxyClient {

    private final String accountSid;
    private final String authToken;
    private final String host;
    private final int port;
    
    public TwilioRestClient getHttpClient() {
        HttpClient httpClient = createHttpClient();
        return new Builder(accountSid, authToken)
          .httpClient(httpClient)
          .build();
    }

    @SneakyThrows
    private HttpClient createHttpClient() {
        SSLContext sslContext = SSLContextBuilder.create()
          .loadTrustMaterial((chain, authType) -> true)
          .build();
        
        HttpClientBuilder clientBuilder = HttpClientBuilder.create()
          .setSSLContext(sslContext)
          .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
          .setProxy(new HttpHost(host, port))
          .setDefaultRequestConfig(RequestConfig.DEFAULT);

        return new NetworkHttpClient(clientBuilder);
    }

}