package com.baeldung.boot.csfle.config.converter;

import org.bson.BsonBinary;
import org.bson.BsonValue;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import com.baeldung.boot.csfle.config.EncryptionConfig;

public class IntegerConverter implements Converter<Binary, Integer> {

    private EncryptionConfig encryptionConfig;

    public IntegerConverter(EncryptionConfig config) {
        this.encryptionConfig = config;
    }

    @Override
    public Integer convert(Binary source) {
        BsonBinary bin = new BsonBinary(source.getType(), source.getData());
        BsonValue value = encryptionConfig.getEncryption()
            .decrypt(bin);

        return value.asInt32()
            .getValue();
    }
}
