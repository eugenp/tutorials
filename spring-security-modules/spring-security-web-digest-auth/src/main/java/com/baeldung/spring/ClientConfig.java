package com.baeldung.spring;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.baeldung.client.HttpComponentsClientHttpRequestFactoryDigestAuth;

@Configuration
public class ClientConfig {
    private static final String DEFAULT_USER = "user1";
    private static final String DEFAULT_PASS = "user1Pass";

    public ClientConfig() {
        super();
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpHost host = new HttpHost("http", "localhost", 8080);
        CloseableHttpClient client = HttpClientBuilder.create().
          setDefaultCredentialsProvider(provider()).useSystemProperties().build();
        HttpComponentsClientHttpRequestFactory requestFactory =
          new HttpComponentsClientHttpRequestFactoryDigestAuth(host, client);

        return new RestTemplate(requestFactory);
    }

    private CredentialsProvider provider() {
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS.toCharArray());
        //defining null and -1 it applies to any host and any port
        final AuthScope authScope = new AuthScope(null, -1);
        provider.setCredentials(authScope, credentials);
        return provider;
    }

}