package com.baeldung.restexpress.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceDeserializer;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceSerializer;
import org.bson.types.ObjectId;
import org.restexpress.ContentType;
import org.restexpress.serialization.json.JacksonJsonProcessor;

import java.util.UUID;

public class JsonSerializationProcessor
        extends JacksonJsonProcessor {
    public JsonSerializationProcessor() {
        super();
        addSupportedMediaTypes(ContentType.HAL_JSON);
    }

    @Override
    protected void initializeModule(SimpleModule module) {
        super.initializeModule(module);
        // For UUID as entity identifiers...
        module.addDeserializer(UUID.class, new UuidDeserializer());
        module.addSerializer(UUID.class, new UuidSerializer());

        // For MongoDB ObjectId as entity identifiers...
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());

        // Support HalResource (de)serialization.
        module.addDeserializer(HalResource.class, new HalResourceDeserializer());
        module.addSerializer(HalResource.class, new HalResourceSerializer());
    }
}
