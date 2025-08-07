package com.baeldung.restclient;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        try {
            // Timeout configurations
            SocketConfig socketConfig = SocketConfig.custom()
              .setSoTimeout(Timeout.ofSeconds(30))  // Read timeout
              .build();

            ConnectionConfig connectionConfig = ConnectionConfig.custom()
              .setConnectTimeout(Timeout.ofSeconds(30))  // Connect timeout
              .build();

            RequestConfig requestConfig = RequestConfig.custom()
              .setConnectionRequestTimeout(Timeout.ofSeconds(30))  // Pool wait timeout
              .build();

            // Connection pool configuration
            PoolingHttpClientConnectionManager connectionManager =
              PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnPerRoute(20)
                .setMaxConnTotal(100)
                .setDefaultSocketConfig(socketConfig)
                .setDefaultConnectionConfig(connectionConfig)
                .build();

            CloseableHttpClient httpClient = HttpClients.custom()
              .setConnectionManager(connectionManager)
              .setDefaultRequestConfig(requestConfig)
              .build();

            return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        } catch (Exception e) {
            throw new IllegalStateException("Failed to configure RestTemplate", e);
        }
    }
}
