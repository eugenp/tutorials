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
        final Collection<SourceClass> testCollection = new Gson().fromJson(jsonCollection, sourceCollectionType);
        assertEquals(sourceCollection, testCollection);
    }

    @Test
    public void givenArrayOfObjects_whenSerializing_thenMapToJsonCollection() {
        final SourceClass[] sourceArray = { new SourceClass(1, "one"), new SourceClass(2, "two") };
        final String jsonCollection = new Gson().toJson(sourceArray);

        // test
        final String estimatedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(estimatedResult, jsonCollection);
    }

    @Test
    public void givenUsingCustomSerializer_whenSerializingObjectToJsonWithDissimilarFieldNames_thenCorrect() {
        final SourceClass sourceObject = new SourceClass(7, "seven");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassChangingFieldNamesSerializer());
        final Gson gson = gsonBuildr.create();
        final String jsonString = gson.toJson(sourceObject);

        // test
        final String estimatedResult = "{\"otherIntValue\":7,\"otherStringValue\":\"seven\"}";
        assertEquals(estimatedResult, jsonString);
    }

    @Test
    public void givenUsingCustomSerializer_whenSerializingObject_thenFieldIgnored() {
        final SourceClass sourceObject = new SourceClass(7, "seven");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new SourceClassIgnoringExtraFieldsSerializer());
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
        final Date testDate = gson.fromJson(jsonDate, sourceDateType);
        assertTrue(sourceDate.equals(testDate));
    }
}