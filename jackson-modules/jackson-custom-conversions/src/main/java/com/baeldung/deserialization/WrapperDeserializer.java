package com.baeldung.deserialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class WrapperDeserializer extends JsonDeserializer<Wrapper<?>> implements ContextualDeserializer {

    private JavaType type;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        this.type = property.getType()
            .containedType(0);
        return this;
    }

    @Override
    public Wrapper<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Wrapper<?> wrapper = new Wrapper<>();
        wrapper.setValue(deserializationContext.readValue(jsonParser, type));
        return wrapper;
    }

}
