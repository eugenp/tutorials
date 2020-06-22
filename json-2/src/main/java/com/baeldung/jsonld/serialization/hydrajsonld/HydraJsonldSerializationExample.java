package com.baeldung.jsonld.serialization.hydrajsonld;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;

import de.escalon.hypermedia.hydra.serialize.JacksonHydraSerializer;

public class HydraJsonldSerializationExample {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(getJacksonHydraSerializerModule());

        HydraJsonldExamplePerson person = new HydraJsonldExamplePerson();
        person.id = "http://example.com/person/1234";
        person.name = "Example Name";

        System.out.println(mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(person));
    }

    public static SimpleModule getJacksonHydraSerializerModule() {
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
