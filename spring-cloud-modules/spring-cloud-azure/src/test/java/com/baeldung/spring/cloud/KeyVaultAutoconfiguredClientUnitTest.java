package com.baeldung.spring.cloud;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.cloud.azure.Application;
import com.baeldung.spring.cloud.azure.service.KeyVaultAutoconfiguredClient;

@SpringBootTest(classes = Application.class)
public class KeyVaultAutoconfiguredClientUnitTest {

    @Autowired
    @Qualifier(value = "KeyVaultAutoconfiguredClient")
    private KeyVaultAutoconfiguredClient keyVaultAutoconfiguredClient;

    @Test
    void whenANotExistingKeyIsProvided_thenShouldReturnAnError() {
        String secretKey = "mySecret";
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            keyVaultAutoconfiguredClient.getSecret(secretKey);
        });
    }

}
