package com.baeldung.sendgrid;

import java.net.URI;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.sendgrid.Client;
import com.sendgrid.SendGrid;

@TestConfiguration
@EnableConfigurationProperties(SendGridConfigurationProperties.class)
class TestSendGridConfiguration {

    @Value("${server.url}")
    private URI serverUrl;

    @Autowired
    private SendGridConfigurationProperties sendGridConfigurationProperties;

    @Bean
    @Primary
    public SendGrid testSendGrid() throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
            .loadTrustMaterial((chain, authType) -> true)
            .build();

        HttpClientBuilder clientBuilder = HttpClientBuilder.create()
            .setSSLContext(sslContext)
            .setProxy(new HttpHost(serverUrl.getHost(), serverUrl.getPort()));

        Client client = new Client(clientBuilder.build(), true);
        client.buildUri(serverUrl.toString(), null, null);

        String apiKey = sendGridConfigurationProperties.getApiKey();
        return new SendGrid(apiKey, client);
    }

}