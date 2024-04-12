package com.baeldung.restexpress.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeserializer
        extends JsonDeserializer<ObjectId> {
    @Override
    public ObjectId deserialize(JsonParser json, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return (ObjectId) Identifiers.MONGOID.parse(json.getText()).primaryKey();
    }
}
