package com.baeldung.boot.csfle.service;

import java.util.List;

import org.bson.BsonBinary;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.baeldung.boot.csfle.config.EncryptionConfig;
import com.baeldung.boot.csfle.data.Citizen;
import com.baeldung.boot.csfle.data.EncryptedCitizen;
import com.mongodb.client.model.vault.EncryptOptions;

@Service
public class CitizenService {

    public static final String DETERMINISTIC_ALGORITHM = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic";
    public static final String RANDOM_ALGORITHM = "AEAD_AES_256_CBC_HMAC_SHA_512-Random";

    @Autowired
    private MongoTemplate mongo;

    @Autowired
    private EncryptionConfig encryptionConfig;

    public EncryptedCitizen save(Citizen citizen) {
        EncryptedCitizen encryptedCitizen = new EncryptedCitizen(citizen);
        encryptedCitizen.setEmail(encrypt(citizen.getEmail(), DETERMINISTIC_ALGORITHM));
        encryptedCitizen.setBirthYear(encrypt(citizen.getBirthYear(), RANDOM_ALGORITHM));

        return mongo.save(encryptedCitizen);
    }

    public List<Citizen> findAll() {
        return mongo.findAll(Citizen.class);
    }

    public Citizen findByEmail(String email) {
        Query byEmail = new Query(Criteria.where("email")
            .is(encrypt(email, DETERMINISTIC_ALGORITHM)));
        return mongo.findOne(byEmail, Citizen.class);
    }

    public BsonBinary encrypt(Object value, String algorithm) {
        if (value == null)
            return null;

        BsonValue bsonValue = value instanceof Integer 
            ? new BsonInt32((Integer) value) 
            : new BsonString(value.toString());

        EncryptOptions options = new EncryptOptions(algorithm);
        options.keyId(encryptionConfig.getDataKeyId());
        return encryptionConfig.getEncryption()
            .encrypt(bsonValue, options);
    }
}
