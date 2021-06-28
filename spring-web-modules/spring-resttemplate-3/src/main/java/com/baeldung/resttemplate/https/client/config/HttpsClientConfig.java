package com.baeldung.resttemplate.https.client.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpsClientConfig {

    String keystore_file="classpath:keystore/tutorial.p12";

    String truststore_file="classpath:keystore/tutorial.p12";

    String password="baeldung";

    public RestTemplate restTemplateWithoutSsl() {
        return build(disableSsl());
    }

    public RestTemplate restTemplateWithSsl() {
        return build(validateSsl());
    }

    public RestTemplate restTemplate(Environment env) {
        setSslProperties(env);
        return new RestTemplate();
    }

    RestTemplate build(ClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory);
    }

    HttpComponentsClientHttpRequestFactory disableSsl() {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(csf)
            .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

HttpComponentsClientHttpRequestFactory validateSsl() {
    SSLContext sslContext = null;
    try {
        SSLContextBuilder builder = SSLContextBuilder.create();
        if(StringUtils.isNotEmpty(truststore_file))
            sslContext = builder.loadTrustMaterial(ResourceUtils.getFile(truststore_file), password.toCharArray())
                    .loadKeyMaterial(ResourceUtils.getFile(keystore_file), password.toCharArray(), password.toCharArray())
                    .build();
        else
        sslContext = builder
            .loadKeyMaterial(ResourceUtils.getFile(keystore_file), password.toCharArray(), password.toCharArray())
            .build();
    } catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException e) {
        e.printStackTrace();
    }
CloseableHttpClient httpClient = HttpClients.custom()
    .setSSLContext(sslContext)
    .build();
    return new HttpComponentsClientHttpRequestFactory(httpClient);
}

    void setSslProperties(Environment env) {
        System.setProperty("javax.net.ssl.trustStore", env.getProperty("server.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("server.ssl.trust-store-password"));
    }
}
