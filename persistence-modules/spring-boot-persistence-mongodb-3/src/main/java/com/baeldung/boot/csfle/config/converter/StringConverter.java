package com.baeldung.boot.csfle.config.converter;

import org.bson.BsonBinary;
import org.bson.BsonValue;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import com.baeldung.boot.csfle.config.EncryptionConfig;

public class StringConverter implements Converter<Binary, String> {

    private EncryptionConfig encryptionConfig;

    public StringConverter(EncryptionConfig config) {
        this.encryptionConfig = config;
    }

    @Override
    public String convert(Binary source) {
        BsonBinary bin = new BsonBinary(source.getType(), source.getData());
        BsonValue value = encryptionConfig.getEncryption()
            .decrypt(bin);

        return value.asString()
            .getValue();
    }
}
