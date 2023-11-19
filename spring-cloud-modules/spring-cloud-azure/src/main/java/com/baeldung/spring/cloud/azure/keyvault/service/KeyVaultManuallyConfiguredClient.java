package com.baeldung.spring.cloud.azure.keyvault.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.baeldung.spring.cloud.azure.keyvault.data.KeyVaultProperties;

@EnableConfigurationProperties(KeyVaultProperties.class)
@Component("KeyVaultManuallyConfiguredClient")
public class KeyVaultManuallyConfiguredClient implements KeyVaultClient {

    private KeyVaultProperties keyVaultProperties;

    private SecretClient secretClient;

    @Override
    public SecretClient getSecretClient() {
        if (secretClient == null) {
            secretClient = new SecretClientBuilder().vaultUrl(keyVaultProperties.getVaultUrl())
              .credential(new ClientSecretCredentialBuilder().tenantId(keyVaultProperties.getTenantId())
                .clientId(keyVaultProperties.getClientId())
                .clientSecret(keyVaultProperties.getClientSecret())
                .build())
              .buildClient();
        }
        return secretClient;
    }
}
