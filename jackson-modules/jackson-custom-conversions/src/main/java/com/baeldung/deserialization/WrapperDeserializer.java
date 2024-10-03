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

    public WrapperDeserializer() {
        // Default constructor
    }

    private WrapperDeserializer(JavaType type) {
        this.type = type;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        JavaType wrapperType = property.getType().containedType(0);
        return new WrapperDeserializer(wrapperType);
    }

    @Override
    public Wrapper<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Wrapper<?> wrapper = new Wrapper<>();
        wrapper.setValue(deserializationContext.readValue(jsonParser, type));
        return wrapper;
    }

}
