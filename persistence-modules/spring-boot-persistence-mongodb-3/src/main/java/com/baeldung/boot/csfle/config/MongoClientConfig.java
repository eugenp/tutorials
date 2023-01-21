package com.baeldung.boot.csfle.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new StringConverter(encryptionConfig), new IntegerConverter(encryptionConfig)));
    }

    @Override
    public MongoClient mongoClient() {
        MongoClient client;
        try {
            client = MongoClients.create(clientSettings());

            ClientEncryption encryption = createClientEncryption();
            encryptionConfig.setDataKeyId(createOrRetrieveDataKey(client, encryption));

            return client;
        } catch (IOException e) {
            throw new IllegalStateException("unable to create client", e);
        }
    }

    private BsonBinary createOrRetrieveDataKey(MongoClient client, ClientEncryption encryption) {
        MongoNamespace namespace = new MongoNamespace(encryptionConfig.getKeyVaultNamespace());
        MongoCollection<Document> keyVault = client.getDatabase(namespace.getDatabaseName())
            .getCollection(namespace.getCollectionName());

        Bson query = Filters.in("keyAltNames", encryptionConfig.getKeyVaultAlias());
        BsonDocument key = keyVault.withDocumentClass(BsonDocument.class)
            .find(query)
            .first();

        if (key == null) {
            keyVault.createIndex(Indexes.ascending("keyAltNames"), new IndexOptions().unique(true)
                .partialFilterExpression(Filters.exists("keyAltNames")));

            DataKeyOptions options = new DataKeyOptions();
            options.keyAltNames(Arrays.asList(encryptionConfig.getKeyVaultAlias()));
            return encryption.createDataKey("local", options);
        } else {
            return (BsonBinary) key.get("_id");
        }
    }

    private ClientEncryption createClientEncryption() throws FileNotFoundException, IOException {
        Map<String, Map<String, Object>> kmsProviders = LocalKmsUtils.providersMap(encryptionConfig.getMasterKeyPath());

        ClientEncryptionSettings encryptionSettings = ClientEncryptionSettings.builder()
            .keyVaultMongoClientSettings(clientSettings())
            .keyVaultNamespace(encryptionConfig.getKeyVaultNamespace())
            .kmsProviders(kmsProviders)
            .build();

        encryptionConfig.setEncryption(ClientEncryptions.create(encryptionSettings));
        return encryptionConfig.getEncryption();
    }

    private MongoClientSettings clientSettings() throws FileNotFoundException, IOException {
        Builder settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(uri));

        if (encryptionConfig.getAutoDecryption()) {
            settings.autoEncryptionSettings(AutoEncryptionSettings.builder()
                .keyVaultNamespace(encryptionConfig.getKeyVaultNamespace())
                .kmsProviders(LocalKmsUtils.providersMap(encryptionConfig.getMasterKeyPath()))
                .bypassAutoEncryption(true)
                .build());
        }

        return settings.build();
    }
}
