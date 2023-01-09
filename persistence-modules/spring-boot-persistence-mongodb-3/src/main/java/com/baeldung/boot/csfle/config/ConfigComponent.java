package com.baeldung.boot.csfle.config;

import org.bson.BsonBinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.vault.ClientEncryption;

@Component
public class ConfigComponent {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    @Value("${com.baeldung.csfle.kms-provider:local}")
    private String kmsProvider;

    @Value("${com.baeldung.csfle.master-key-path}")
    private String masterKeyPath;

    @Value("${com.baeldung.csfle.key-vault.namespace}")
    private String keyVaultNamespace;

    @Value("${com.baeldung.csfle.key-vault.alias}")
    private String keyVaultAlias;

    @Value("${com.baeldung.csfle.auto-decryption:false}")
    private Boolean autoDecryption;

    private ClientEncryption encryption;

    private BsonBinary dataKeyId;

    public void setEncryption(ClientEncryption encryption) {
        this.encryption = encryption;
    }

    public ClientEncryption getEncryption() {
        return encryption;
    }

    public void setDataKeyId(BsonBinary dataKeyId) {
        this.dataKeyId = dataKeyId;
    }

    public BsonBinary getDataKeyId() {
        return dataKeyId;
    }

    public String getUri() {
        return uri;
    }

    public String getDb() {
        return db;
    }

    public String getKmsProvider() {
        return kmsProvider;
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
