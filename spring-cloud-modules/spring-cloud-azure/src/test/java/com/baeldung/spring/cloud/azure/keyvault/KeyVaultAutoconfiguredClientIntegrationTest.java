package com.baeldung.spring.cloud.azure.keyvault;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.cloud.azure.keyvault.service.KeyVaultAutoconfiguredClient;

@SpringBootTest(classes = Application.class)
class KeyVaultAutoconfiguredClientIntegrationTest {

    @Autowired
    @Qualifier(value = "KeyVaultAutoconfiguredClient")
    private KeyVaultAutoconfiguredClient keyVaultAutoconfiguredClient;

    @Test
    void whenANotExistingKeyIsProvided_thenShouldReturnAnError() {
        String secretKey = "mySecret";
        Assertions.assertThrows(NoSuchElementException.class, () -> keyVaultAutoconfiguredClient.getSecret(secretKey));
    }
}
