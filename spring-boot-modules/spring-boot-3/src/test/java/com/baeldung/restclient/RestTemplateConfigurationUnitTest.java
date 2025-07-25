package com.baeldung.restclient;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = RestTemplateConfiguration.class)
public class RestTemplateConfigurationUnitTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void givenSpringContext_whenRestTemplateBeanRetrieved_thenReturnsProperlyConfiguredInstance() {
        // When - We retrieve the RestTemplate bean
        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        // Then - Verify it's properly configured
        assertThat(restTemplate)
                .isNotNull()
                .extracting(RestTemplate::getRequestFactory)
                .isInstanceOf(HttpComponentsClientHttpRequestFactory.class);

        HttpComponentsClientHttpRequestFactory factory =
                (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();

        assertThat(factory.getHttpClient())
                .isInstanceOfSatisfying(CloseableHttpClient.class, client -> {
                    assertThat(client).isNotNull();
                });
    }
}
