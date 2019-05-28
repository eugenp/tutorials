package com.baeldung.jest;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JestClientConfiguration {

    @Value("${elastic.url:http://localhost:9200}")
    private String elasticUrl;

    /**
     * Create a JEST client bean.
     * @return JestClient
     */
    @Bean
    public JestClient jestClient()
    {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig.Builder(elasticUrl)
                        .multiThreaded(true)
                        .defaultMaxTotalConnectionPerRoute(2)
                        .maxTotalConnection(20)
                        .build());
        return factory.getObject();
    }
}
