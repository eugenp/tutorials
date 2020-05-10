package com.baeldung.jackson.try1;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class RestLoaderRequestDeserializer extends StdDeserializer<RestLoaderRequest<IEntity>> {
    private static final long serialVersionUID = -4245207329377196889L;

    public RestLoaderRequestDeserializer() {
        this(null);
    }

    public RestLoaderRequestDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public RestLoaderRequest<IEntity> deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            final ObjectCodec objectCodec = jp.getCodec();
            final JsonNode node = objectCodec.readTree(jp);
            final String className = node.get("className")
                .textValue();
            final String fieldName = node.get("fieldName")
                .textValue();

            final Class<?> clazz = Class.forName(className);

            final JsonNode rawEntityNode = node.get("entity");
            // How to deserialize rawEntityNode to T based on className ?

            final RestLoaderRequest<IEntity> request = new RestLoaderRequest<IEntity>();
            request.setClassName(className);
            request.setFieldName(fieldName);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}