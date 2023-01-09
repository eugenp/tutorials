package com.baeldung.boot.csfle.config;

import java.util.Arrays;
import java.util.Map;

import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.baeldung.boot.csfle.config.converter.IntegerConverter;
import com.baeldung.boot.csfle.config.converter.StringConverter;
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

    @Autowired
    private ConfigComponent config;

    @Override
    protected String getDatabaseName() {
        return config.getDb();
    }

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new StringConverter(config), new IntegerConverter(config)));
    }

    @Override
    public MongoClient mongoClient() {
        MongoClient client = MongoClients.create(clientSettings());

        ClientEncryption encryption = createClientEncryption();
        config.setDataKeyId(createOrRetrieveDataKey(client, encryption));

        return client;
    }

    private BsonBinary createOrRetrieveDataKey(MongoClient client, ClientEncryption encryption) {
        MongoNamespace namespace = new MongoNamespace(config.getKeyVaultNamespace());
        MongoCollection<Document> keyVault = client.getDatabase(namespace.getDatabaseName())
            .getCollection(namespace.getCollectionName());

        Bson query = Filters.in("keyAltNames", config.getKeyVaultAlias());
        BsonDocument key = keyVault.withDocumentClass(BsonDocument.class)
            .find(query)
            .first();

        if (key == null) {
            keyVault.createIndex(Indexes.ascending("keyAltNames"), new IndexOptions().unique(true)
                .partialFilterExpression(Filters.exists("keyAltNames")));

            DataKeyOptions options = new DataKeyOptions();
            options.keyAltNames(Arrays.asList(config.getKeyVaultAlias()));
            return encryption.createDataKey(config.getKmsProvider(), options);
        } else {
            return (BsonBinary) key.get("_id");
        }
    }

    private ClientEncryption createClientEncryption() {
        Map<String, Map<String, Object>> kmsProviders = LocalKmsUtils.providersMap(config.getMasterKeyPath());

        ClientEncryptionSettings encryptionSettings = ClientEncryptionSettings.builder()
            .keyVaultMongoClientSettings(clientSettings())
            .keyVaultNamespace(config.getKeyVaultNamespace())
            .kmsProviders(kmsProviders)
            .build();

        config.setEncryption(ClientEncryptions.create(encryptionSettings));
        return config.getEncryption();
    }

    private MongoClientSettings clientSettings() {
        Builder settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(config.getUri()));

        if (config.getAutoDecryption()) {
            settings.autoEncryptionSettings(AutoEncryptionSettings.builder()
                .keyVaultNamespace(config.getKeyVaultNamespace())
                .kmsProviders(LocalKmsUtils.providersMap(config.getMasterKeyPath()))
                .bypassAutoEncryption(true)
                .build());
        }

        return settings.build();
    }
}
