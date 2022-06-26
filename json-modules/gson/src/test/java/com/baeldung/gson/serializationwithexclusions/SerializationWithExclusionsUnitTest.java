package com.baeldung.gson.serializationwithexclusions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class SerializationWithExclusionsUnitTest {

    final String expectedResult = "{\"id\":1,\"name\":\"foo\",\"subclass\":{\"id\":42,\"description\":\"the answer\"}}";

    @Test
    public void givenClassWithTransientFields_whenSerializing_thenCorrectWithoutTransientFields() {
        MyClassWithTransientFields source = new MyClassWithTransientFields(1L, "foo", "bar", new MySubClassWithTransientFields(42L, "the answer", "Verbose field which we don't want to be serialized"));
        String jsonString = new Gson().toJson(source);
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenClassAnnotated_whenSerializing_thenCorrectWithoutNotAnnotatedFields() {
        MyClassWithAnnotatedFields source = new MyClassWithAnnotatedFields(1L, "foo", "bar", new MySubClassWithAnnotatedFields(42L, "the answer", "Verbose field which we don't want to be serialized"));
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .create();
        String jsonString = gson.toJson(source);

        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenExclusionStrategyByClassesAndFields_whenSerializing_thenFollowStrategy() {
        MyClass source = new MyClass(1L, "foo", "bar", new MySubClass(42L, "the answer", "Verbose field which we don't want to be serialized"));
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                if (field.getDeclaringClass() == MyClass.class && field.getName()
                    .equals("other"))
                    return true;
                if (field.getDeclaringClass() == MySubClass.class && field.getName()
                    .equals("otherVerboseInfo"))
                    return true;
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(strategy)
            .create();
        String jsonString = gson.toJson(source);

        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenExclusionStrategyByStartsWith_whenSerializing_thenFollowStrategy() {
        MyClass source = new MyClass(1L, "foo", "bar", new MySubClass(42L, "the answer", "Verbose field which we don't want to be serialized"));
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().startsWith("other");
            }
        };
        Gson gson = new GsonBuilder().setExclusionStrategies(strategy)
            .create();
        String jsonString = gson.toJson(source);

        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenExclusionStrategyByCustomAnnotation_whenSerializing_thenFollowStrategy() {
        MyClassWithCustomAnnotatedFields source = new MyClassWithCustomAnnotatedFields(1L, "foo", "bar", new MySubClassWithCustomAnnotatedFields(42L, "the answer", "Verbose field which we don't want to be serialized"));
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getAnnotation(Exclude.class) != null;
            }

        };

        Gson gson = new GsonBuilder().setExclusionStrategies(strategy)
            .create();
        String jsonString = gson.toJson(source);
        assertEquals(expectedResult, jsonString);
    }

}