package com.baeldung.springbootsslbundles;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.NoSuchSslBundleException;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Component
public class SecureRestTemplateConfig {
    private final SSLContext sslContext;

    @Autowired
    public SecureRestTemplateConfig(SslBundles sslBundles) throws NoSuchSslBundleException {
        SslBundle sslBundle = sslBundles.getBundle("secure-service");
        this.sslContext = sslBundle.createSslContext();
    }

    @Bean
    public RestTemplate secureRestTemplate() {
        final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create().setSslContext(this.sslContext).build();
        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
        HttpClient httpClient = HttpClients.custom().setConnectionManager(cm).evictExpiredConnections().build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}