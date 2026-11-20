package com.baeldung.proxyauth;

import java.io.IOException;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class ProxiedApacheHttpClient {

    private ProxiedApacheHttpClient() {
    }

    public static CloseableHttpClient createClient(ProxyConfig config) {
        HttpHost proxy = new HttpHost(config.getHost(), config.getPort());

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(config.getUsername(), config.getPassword()
            .toCharArray()));

        return HttpClients.custom()
            .setProxy(proxy)
            .setDefaultCredentialsProvider(credentialsProvider)
            .build();
    }

    public static String sendRequest(CloseableHttpClient client, String url) throws IOException, HttpException {
        HttpGet request = new HttpGet(url);

        return client.execute(request, response -> EntityUtils.toString(response.getEntity()));
    }
}
