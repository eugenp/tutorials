package org.baeldung.gson.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonSerializationTest {

    @Test
    public void givenCollection_whenSerializing_thenCorrect() {
        final Collection<SourceClass> sourceCollection = Lists.newArrayList(new SourceClass(1, "one"), new SourceClass(2, "two"));
        final Type sourceCollectionType = new TypeToken<Collection<SourceClass>>() {
        }.getType();
        final String jsonCollection = new Gson().toJson(sourceCollection, sourceCollectionType);

        // test
        final String estimatedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(estimatedResult, jsonCollection);
    }

    @Test
    public void givenArrayOfObjects_whenSerializing_thenCorrect() {
        final SourceClass[] sourceArray = { new SourceClass(1, "one"), new SourceClass(2, "two") };
        final String jsonString = new Gson().toJson(sourceArray);

        // test
        final String estimatedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(estimatedResult, jsonString);
    }

    @Test
    public void givenUsingCustomSerializer_whenChangingNameOfFieldOnSerializing_thenCorrect() {
        final SourceClass sourceObject = new SourceClass(7, "seven");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new DifferentNameSerializer());
        final Gson gson = gsonBuildr.create();
        final String jsonString = gson.toJson(sourceObject);

        // test
        final String estimatedResult = "{\"otherIntValue\":7,\"otherStringValue\":\"seven\"}";
        assertEquals(estimatedResult, jsonString);
    }

    @Test
    public void givenIgnoringAField_whenSerializingWithCustomSerializer_thenFieldIgnored() {
        final SourceClass sourceObject = new SourceClass(7, "seven");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new IgnoringFieldsSerializer());
        final Gson gson = gsonBuildr.create();
        final String jsonString = gson.toJson(sourceObject);

        // test
        final String estimatedResult = "{\"intValue\":7}";
        assertEquals(estimatedResult, jsonString);
    }

    @Test
    public void givenDate_whenSerializing_thenCorrect() {
        final Date sourceDate = new Date(1000000L);
        final Gson gson = new Gson();
        final Type sourceDateType = new TypeToken<Date>() {
        }.getType();
        final String jsonDate = gson.toJson(sourceDate, sourceDateType);

        // test
        final String estimatedResult = "\"Jan 1, 1970 3:16:40 AM\"";
        assertTrue(jsonDate.equals(estimatedResult));
    }

}