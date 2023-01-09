package com.baeldung.boot.csfle.config.converter;

import org.bson.BsonBinary;
import org.bson.BsonValue;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import com.baeldung.boot.csfle.config.ConfigComponent;

public class IntegerConverter implements Converter<Binary, Integer> {

    private ConfigComponent config;

    public IntegerConverter(ConfigComponent config) {
        this.config = config;
    }

    @Override
    public Integer convert(Binary source) {
        BsonBinary bin = new BsonBinary(source.getType(), source.getData());
        BsonValue value = config.getEncryption()
            .decrypt(bin);

        return value.asInt32()
            .getValue();
    }
}
