package com.baeldung.jacksonjr;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.jr.annotationsupport.JacksonAnnotationExtension;
import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.JacksonJrExtension;
import com.fasterxml.jackson.jr.ob.api.ExtensionContext;

public class JacksonJrFeatures {

    public static String jsonObject() throws IOException {
        return JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .asString(new LinkedHashMap<String, Object>() {{
                put("name", "John Doe");
                put("age", 30);
            }});
    }

    public static String jsonComposer() throws IOException {
        return JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .composeString()
            .startObject()
            .startArrayField("objectArray")
            .startObject()
            .put("name", "name1")
            .put("age", 11)
            .end()
            .startObject()
            .put("name", "name2")
            .put("age", 12)
            .end()
            .end()
            .startArrayField("array")
            .add(1)
            .add(2)
            .add(3)
            .end()
            .startObjectField("object")
            .put("name", "name3")
            .put("age", 13)
            .end()
            .put("last", true)
            .end()
            .finish();
    }

    public static String objectSerialization(Person person) throws IOException {
        return JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .asString(person);
    }

    public static String objectAnnotationSerialization(Person person) throws IOException {
        return JSON.builder()
            .register(JacksonAnnotationExtension.std)
            .build()
            .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .asString(person);
    }

    public static String customObjectSerialization(Person person) throws IOException {
        return JSON.builder()
            .register(new JacksonJrExtension() {
                @Override
                protected void register(ExtensionContext extensionContext) {
                    extensionContext.insertProvider(new MyHandlerProvider());
                }
            })
            .build()
            .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .asString(person);
    }

    public static Person objectDeserialization(String json) throws IOException {
        return JSON.std.with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .beanFrom(Person.class, json);
    }

    public static Person customObjectDeserialization(String json) throws IOException {
        return JSON.builder()
            .register(new JacksonJrExtension() {
                @Override
                protected void register(ExtensionContext extensionContext) {
                    extensionContext.insertProvider(new MyHandlerProvider());
                }
            })
            .build()
            .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .beanFrom(Person.class, json);
    }
}
