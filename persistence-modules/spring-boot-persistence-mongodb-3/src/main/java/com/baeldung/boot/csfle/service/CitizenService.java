package com.baeldung.boot.csfle.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonBinary;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.baeldung.boot.csfle.config.EncryptionConfig;
import com.baeldung.boot.csfle.data.Citizen;
import com.baeldung.boot.csfle.data.EncryptedCitizen;
import com.mongodb.client.model.vault.EncryptOptions;
import com.mongodb.client.vault.ClientEncryption;

@Service
public class CitizenService {

    public static final String DETERMINISTIC_ALGORITHM = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic";
    public static final String RANDOM_ALGORITHM = "AEAD_AES_256_CBC_HMAC_SHA_512-Random";

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private EncryptionConfig encryptionConfig;

    @Autowired
    private ClientEncryption clientEncryption;

    public Object save(Citizen citizen) {
        if (encryptionConfig.isAutoEncryption()) {
            return mongo.save(citizen);
        } else {
            EncryptedCitizen encryptedCitizen = new EncryptedCitizen(citizen);
            encryptedCitizen.setEmail(encrypt(citizen.getEmail(), DETERMINISTIC_ALGORITHM));
            encryptedCitizen.setBirthYear(encrypt(citizen.getBirthYear(), RANDOM_ALGORITHM));

            return mongo.save(encryptedCitizen);
        }
    }

    public List<Citizen> findAll() {
        if (!encryptionConfig.isAutoDecryption()) {
            List<EncryptedCitizen> allEncrypted = mongo.findAll(EncryptedCitizen.class);

            return allEncrypted.stream()
                .map(this::decrypt)
                .collect(Collectors.toList());
        } else {
            return mongo.findAll(Citizen.class);
        }
    }

    public Citizen findByEmail(String email) {
        Criteria emailCriteria = Criteria.where("email");
        if (encryptionConfig.isAutoEncryption()) {
            emailCriteria.is(email);
        } else {
            emailCriteria
                .is(encrypt(email, DETERMINISTIC_ALGORITHM));
        }

        Query byEmail = new Query(emailCriteria);
        if (encryptionConfig.isAutoDecryption()) {
            return mongo.findOne(byEmail, Citizen.class);
        } else {
            EncryptedCitizen encryptedCitizen = mongo.findOne(byEmail, EncryptedCitizen.class);
            return decrypt(encryptedCitizen);
        }
    }

    public Binary encrypt(Object value, String algorithm) {
        if (value == null)
            return null;

        BsonValue bsonValue;
        if (value instanceof Integer) {
            bsonValue = new BsonInt32((Integer) value);
        } else if (value instanceof String) {
            bsonValue = new BsonString((String) value);
        } else {
            throw new IllegalArgumentException("unsupported type: " + value.getClass());
        }

        EncryptOptions options = new EncryptOptions(algorithm);
        options.keyId(encryptionConfig.getDataKeyId());

        BsonBinary encryptedValue = clientEncryption.encrypt(bsonValue, options);
        return new Binary(encryptedValue.getType(), encryptedValue.getData());
    }

    public BsonValue decryptProperty(Binary value) {
        if (value == null)
            return null;

        return clientEncryption.decrypt(new BsonBinary(value.getType(), value.getData()));
    }

    private Citizen decrypt(EncryptedCitizen encrypted) {
        if (encrypted == null)
            return null;

        Citizen citizen = new Citizen(encrypted);

        BsonValue decryptedBirthYear = decryptProperty(encrypted.getBirthYear());
        if (decryptedBirthYear != null) {
            citizen.setBirthYear(decryptedBirthYear.asInt32()
                .intValue());
        }

        BsonValue decryptedEmail = decryptProperty(encrypted.getEmail());
        if (decryptedEmail != null) {
            citizen.setEmail(decryptedEmail.asString()
                .getValue());
        }

        return citizen;
    }
}
