package com.baeldung.jsonld.serialization.hydrajsonld;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;

import de.escalon.hypermedia.hydra.serialize.JacksonHydraSerializer;

public class HydraJsonldSerializationUnitTest {
    @Test
    void givenAHydraJsonldAnnotatedObject_whenJacksonHydraSerializerIsUsed_thenAJsonLdDocumentIsGenerated() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(getJacksonHydraSerializerModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Person person = new Person("http://example.com/person/1234", "Example Name");

        String personJsonLd = objectMapper.writeValueAsString(person);

        assertEquals("{"
            + "\"@context\":{"
            +     "\"@vocab\":\"http://example.com/vocab/\","
            +     "\"name\":\"fullName\""
            + "},"
            + "\"@type\":\"person\","
            + "\"name\":\"Example Name\","
            + "\"@id\":\"http://example.com/person/1234\""
            + "}", personJsonLd);
    }

    static SimpleModule getJacksonHydraSerializerModule() {
        return new SimpleModule() {

            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);

                context.addBeanSerializerModifier(new BeanSerializerModifier() {

                    @Override
                    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {

                        if (serializer instanceof BeanSerializerBase) {
                            return new JacksonHydraSerializer((BeanSerializerBase) serializer);
                        } else {
                            return serializer;
                        }
                    }
                });
            }
        };
    }
}
