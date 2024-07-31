package com.baeldung.registrypostprocessor;

import com.baeldung.registrypostprocessor.bean.ApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = RegistryPostProcessorApplication.class)
public class ApiClientConfigurationUnitTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void givenBeansRegistered_whenConnect_thenConnected() {
        ApiClient exampleClient = (ApiClient) context.getBean("apiClient_example");
        Assertions.assertEquals("Connecting to example at https://api.example.com", exampleClient.getConnectionProperties());

        ApiClient anotherExampleClient = (ApiClient) context.getBean("apiClient_anotherexample");
        Assertions.assertEquals("Connecting to anotherexample at https://api.anotherexample.com", anotherExampleClient.getConnectionProperties());
    }
}