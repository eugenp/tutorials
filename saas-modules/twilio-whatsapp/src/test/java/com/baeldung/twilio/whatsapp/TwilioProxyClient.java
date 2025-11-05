package com.baeldung.twilio.whatsapp;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import com.twilio.http.HttpClient;
import com.twilio.http.NetworkHttpClient;
import com.twilio.http.TwilioRestClient;
import com.twilio.http.TwilioRestClient.Builder;

class TwilioProxyClient {

    private final String accountSid;
    private final String authToken;
    private final String host;
    private final int port;
    
    public TwilioProxyClient(String accountSid, String authToken, String host, int port) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.host = host;
        this.port = port;
    }

    public TwilioRestClient createHttpClient() throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
          .loadTrustMaterial((chain, authType) -> true)
          .build();
        
        HttpClientBuilder clientBuilder = HttpClientBuilder.create()
          .setSSLContext(sslContext)
          .setProxy(new HttpHost(host, port));

        HttpClient httpClient = new NetworkHttpClient(clientBuilder);
        return new Builder(accountSid, authToken)
          .httpClient(httpClient)
          .build();
    }

}