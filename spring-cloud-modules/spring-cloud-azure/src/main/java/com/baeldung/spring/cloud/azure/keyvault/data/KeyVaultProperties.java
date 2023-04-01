package com.baeldung.spring.cloud.azure.keyvault.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("azure.keyvault")
@ConstructorBinding
public class KeyVaultProperties {
    private String vaultUrl;
    private String tenantId;
    private String clientId;
    private String clientSecret;

    public KeyVaultProperties(String vaultUrl, String tenantId, String clientId, String clientSecret) {
        this.vaultUrl = vaultUrl;
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getVaultUrl() {
        return vaultUrl;
    }

    public void setVaultUrl(String vaultUrl) {
        this.vaultUrl = vaultUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
