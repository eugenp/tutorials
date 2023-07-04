package com.baeldung.boot.csfle.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.AutoEncryptionSettings;
import com.mongodb.ClientEncryptionSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.vault.ClientEncryption;
import com.mongodb.client.vault.ClientEncryptions;

@Configuration
public class MongoClientConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    @Autowired
    private EncryptionConfig encryptionConfig;

    @Override
    protected String getDatabaseName() {
        return db;
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        try {
            ClientEncryption encryption = clientEncryption();
            encryptionConfig.setDataKeyId(createOrRetrieveDataKey(encryption));
            return MongoClients.create(clientSettings());
        } catch (IOException e) {
            throw new IllegalStateException("unable to create client", e);
        }
    }

    @Bean
    public ClientEncryption clientEncryption() throws FileNotFoundException, IOException {
        Map<String, Map<String, Object>> kmsProviders = LocalKmsUtils.providersMap(encryptionConfig.getMasterKeyPath());

        ClientEncryptionSettings encryptionSettings = ClientEncryptionSettings.builder()
            .keyVaultMongoClientSettings(clientSettings())
            .keyVaultNamespace(encryptionConfig.getKeyVaultNamespace())
            .kmsProviders(kmsProviders)
            .build();

        return ClientEncryptions.create(encryptionSettings);
    }

    private BsonBinary createOrRetrieveDataKey(ClientEncryption encryption) {
        BsonDocument key = encryption.getKeyByAltName(encryptionConfig.getKeyVaultAlias());
        if (key == null) {
            createKeyUniqueIndex();

            DataKeyOptions options = new DataKeyOptions();
            options.keyAltNames(Arrays.asList(encryptionConfig.getKeyVaultAlias()));
            return encryption.createDataKey("local", options);
        } else {
            return (BsonBinary) key.get("_id");
        }
    }

    private void createKeyUniqueIndex() {
        try (MongoClient client = MongoClients.create(MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri))
            .build())) {
            MongoNamespace namespace = new MongoNamespace(encryptionConfig.getKeyVaultNamespace());
            MongoCollection<Document> keyVault = client.getDatabase(namespace.getDatabaseName())
                .getCollection(namespace.getCollectionName());

            keyVault.createIndex(Indexes.ascending("keyAltNames"), new IndexOptions().unique(true)
                .partialFilterExpression(Filters.exists("keyAltNames")));
        }
    }

    private MongoClientSettings clientSettings() throws FileNotFoundException, IOException {
        Builder settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri));

        if (encryptionConfig.isAutoDecryption()) {
            AutoEncryptionSettings.Builder builder = AutoEncryptionSettings.builder()
                .keyVaultNamespace(encryptionConfig.getKeyVaultNamespace())
                .kmsProviders(LocalKmsUtils.providersMap(encryptionConfig.getMasterKeyPath()));

            if (encryptionConfig.isAutoEncryption() && encryptionConfig.getDataKeyId() != null) {
                File autoEncryptionLib = encryptionConfig.getAutoEncryptionLib();
                if (!autoEncryptionLib.isFile()) {
                    throw new IllegalArgumentException("encryption lib must be an existing file");
                }

                Map<String, Object> map = new HashMap<>();
                map.put("cryptSharedLibRequired", true);
                map.put("cryptSharedLibPath", autoEncryptionLib.toString());
                builder.extraOptions(map);
                
                String keyUuid = encryptionConfig.dataKeyIdUuid();
                HashMap<String, BsonDocument> schemaMap = new HashMap<>();
                schemaMap.put(getDatabaseName() + ".citizens",
                    BsonDocument.parse("{"
                        + "  bsonType: \"object\","
                        + "  encryptMetadata: {"
                        + "    keyId: [UUID(\"" + keyUuid + "\")]"
                        + "  },"
                        + "  properties: {"
                        + "    email: {"
                        + "      encrypt: {"
                        + "        bsonType: \"string\","
                        + "        algorithm: \"AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic\""
                        + "      }"
                        + "    },"
                        + "    birthYear: {"
                        + "      encrypt: {"
                        + "        bsonType: \"int\","
                        + "        algorithm: \"AEAD_AES_256_CBC_HMAC_SHA_512-Random\""
                        + "      }"
                        + "    }"
                        + "  }"
                        + "}"));
                builder.schemaMap(schemaMap);
            } else {
                builder.bypassAutoEncryption(true);
            }

            settings.autoEncryptionSettings(builder.build());
        }

        return settings.build();
    }
}
