package com.baeldung.restclient;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.HostnameVerificationPolicy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        try {
            // WARNING: Disabling SSL verification is unsafe for production!
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial((chain, authType) -> true)  // Trust all certificates
                    .build();

            DefaultClientTlsStrategy tlsStrategy = new DefaultClientTlsStrategy(
                    sslContext,
                    HostnameVerificationPolicy.CLIENT,
                    NoopHostnameVerifier.INSTANCE  // Disables hostname checks
            );

            // Timeout configurations
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoTimeout(Timeout.ofSeconds(30))  // Read timeout
                    .build();

            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setConnectTimeout(Timeout.ofSeconds(10))  // Connection timeout
                    .build();

            // Connection pool (optimized for production)
            PoolingHttpClientConnectionManager connectionManager =
                    PoolingHttpClientConnectionManagerBuilder.create()
                            .setMaxConnPerRoute(20)
                            .setMaxConnTotal(100)
                            .setTlsSocketStrategy(tlsStrategy)
                            .setDefaultSocketConfig(socketConfig)
                            .setDefaultConnectionConfig(connectionConfig)
                            .build();

            // Request-level timeout (waiting for a connection from the pool)
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(Timeout.ofSeconds(5))
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
