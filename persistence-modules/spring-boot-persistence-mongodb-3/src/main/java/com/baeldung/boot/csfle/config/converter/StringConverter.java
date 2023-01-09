package com.baeldung.boot.csfle.config.converter;

import org.bson.BsonBinary;
import org.bson.BsonValue;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import com.baeldung.boot.csfle.config.ConfigComponent;

public class StringConverter implements Converter<Binary, String> {

    private ConfigComponent config;

    public StringConverter(ConfigComponent config) {
        this.config = config;
    }

    @Override
    public String convert(Binary source) {
        BsonBinary bin = new BsonBinary(source.getType(), source.getData());
        BsonValue value = config.getEncryption()
            .decrypt(bin);

        return value.asString()
            .getValue();
    }
}
