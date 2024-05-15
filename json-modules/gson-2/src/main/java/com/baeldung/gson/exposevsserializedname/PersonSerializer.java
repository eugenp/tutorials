package com.baeldung.gson.exposevsserializedname;

import com.baeldung.gson.entities.Country;
import com.baeldung.gson.entities.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PersonSerializer {
    private static final Gson configuredGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private static final Gson defaultGson = new Gson();

    public static String serializeWithConfiguredGson(Person person) {
        return configuredGson.toJson(person);
    }

    public static String serializeWithDefaultGson(Person person) {
        return defaultGson.toJson(person);
    }

    public static String toJsonString(Object obj) {
        return defaultGson.toJson(obj);
    }

    public static Country fromJsonString(String json) {
        return defaultGson.fromJson(json, Country.class);
    }
}
