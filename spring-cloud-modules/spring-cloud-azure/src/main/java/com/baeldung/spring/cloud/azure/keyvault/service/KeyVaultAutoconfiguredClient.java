package com.baeldung.spring.cloud.azure.keyvault.service;

import org.springframework.stereotype.Component;

import com.azure.security.keyvault.secrets.SecretClient;

@Component("KeyVaultAutoconfiguredClient")
public class KeyVaultAutoconfiguredClient implements KeyVaultClient {
    private final SecretClient secretClient;

    public KeyVaultAutoconfiguredClient(SecretClient secretClient) {
        this.secretClient = secretClient;
    }

    @Override
    public SecretClient getSecretClient() {
        return secretClient;
    }
}
