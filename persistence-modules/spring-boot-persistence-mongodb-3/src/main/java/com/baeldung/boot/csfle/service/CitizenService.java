package com.baeldung.boot.csfle.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    private final MongoTemplate mongo;
    private final EncryptionConfig encryptionConfig;
    private final ClientEncryption clientEncryption;

    public CitizenService(MongoTemplate mongo, EncryptionConfig encryptionConfig, ClientEncryption clientEncryption) {
        this.mongo = mongo;
        this.encryptionConfig = encryptionConfig;
        this.clientEncryption = clientEncryption;
    }

    public Object save(Citizen citizen) {
        if (encryptionConfig.isAutoEncryption()) {
            return mongo.save(citizen);
        } else {
            EncryptedCitizen encryptedCitizen = new EncryptedCitizen();
            encryptedCitizen.setName(citizen.getName());
            if (citizen.getEmail() != null) {
                encryptedCitizen.setEmail(encrypt(citizen.getEmail(), DETERMINISTIC_ALGORITHM));
            } else {
                encryptedCitizen.setEmail(null);

            }
            if (citizen.getBirthYear() != null) {
                encryptedCitizen.setBirthYear(encrypt(citizen.getBirthYear(), RANDOM_ALGORITHM));
            } else {
                encryptedCitizen.setBirthYear(null);
            }

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

    public Binary encrypt(BsonValue bsonValue, String algorithm) {
        Objects.requireNonNull(bsonValue);
        Objects.requireNonNull(algorithm);

        EncryptOptions options = new EncryptOptions(algorithm);
        options.keyId(encryptionConfig.getDataKeyId());

        BsonBinary encryptedValue = clientEncryption.encrypt(bsonValue, options);
        return new Binary(encryptedValue.getType(), encryptedValue.getData());
    }

    public Binary encrypt(String value, String algorithm) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(algorithm);

        return encrypt(new BsonString(value), algorithm);
    }

    public Binary encrypt(Integer value, String algorithm) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(algorithm);

        return encrypt(new BsonInt32(value), algorithm);
    }

    public BsonValue decryptProperty(Binary value) {
        Objects.requireNonNull(value);

        return clientEncryption.decrypt(new BsonBinary(value.getType(), value.getData()));
    }

    private Citizen decrypt(EncryptedCitizen encrypted) {
        Objects.requireNonNull(encrypted);

        Citizen citizen = new Citizen();
        citizen.setName(encrypted.getName());

        BsonValue decryptedBirthYear = encrypted.getBirthYear() != null ? decryptProperty(encrypted.getBirthYear()) : null;
        if (decryptedBirthYear != null) {
            citizen.setBirthYear(decryptedBirthYear.asInt32().intValue());
        }

        BsonValue decryptedEmail = encrypted.getEmail() != null ? decryptProperty(encrypted.getEmail()) : null;
        if (decryptedEmail != null) {
            citizen.setEmail(decryptedEmail.asString().getValue());
        }

        return citizen;
    }
}
