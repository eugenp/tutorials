package com.baeldung.boot.csfle.config;

import org.bson.BsonBinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Value("${com.baeldung.csfle.master-key-path}")
    private String masterKeyPath;

    @Value("${com.baeldung.csfle.key-vault.namespace}")
    private String keyVaultNamespace;

    @Value("${com.baeldung.csfle.key-vault.alias}")
    private String keyVaultAlias;

    @Value("${com.baeldung.csfle.auto-decryption:false}")
    private Boolean autoDecryption;

    private BsonBinary dataKeyId;

    public void setDataKeyId(BsonBinary dataKeyId) {
        this.dataKeyId = dataKeyId;
    }

    public BsonBinary getDataKeyId() {
        return dataKeyId;
    }

    public String getKeyVaultNamespace() {
        return keyVaultNamespace;
    }

    public String getKeyVaultAlias() {
        return keyVaultAlias;
    }

    public String getMasterKeyPath() {
        return masterKeyPath;
    }

    public Boolean getAutoDecryption() {
        return autoDecryption;
    }
}
