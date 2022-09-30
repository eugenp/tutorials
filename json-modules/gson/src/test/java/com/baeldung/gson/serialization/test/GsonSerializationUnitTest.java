package com.baeldung.gson.serialization.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;

import com.baeldung.gson.serialization.DifferentNameSerializer;
import com.baeldung.gson.serialization.IgnoringFieldsNotMatchingCriteriaSerializer;
import com.baeldung.gson.serialization.IgnoringFieldsSerializer;
import com.baeldung.gson.serialization.SourceClass;

import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Test;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonSerializationUnitTest {

    @Test
    public void givenArrayOfObjects_whenSerializing_thenCorrect() {
        final SourceClass[] sourceArray = { new SourceClass(1, "one"), new SourceClass(2, "two") };
        final String jsonString = new Gson().toJson(sourceArray);

        // test
        final String expectedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenCollection_whenSerializing_thenCorrect() {
        final Collection<SourceClass> sourceCollection = Lists.newArrayList(new SourceClass(1, "one"), new SourceClass(2, "two"));
        final String jsonCollection = new Gson().toJson(sourceCollection);

        final String expectedResult = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        assertEquals(expectedResult, jsonCollection);
    }

    @Test
    public void givenUsingCustomSerializer_whenChangingNameOfFieldOnSerializing_thenCorrect() {
        final SourceClass sourceObject = new SourceClass(7, "seven");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new DifferentNameSerializer());
        final String jsonString = gsonBuildr.create().toJson(sourceObject);

        final String expectedResult = "{\"otherIntValue\":7,\"otherStringValue\":\"seven\"}";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenIgnoringAField_whenSerializingWithCustomSerializer_thenFieldIgnored() {
        final SourceClass sourceObject = new SourceClass(7, "seven");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new IgnoringFieldsSerializer());
        final String jsonString = gsonBuildr.create().toJson(sourceObject);

        final String expectedResult = "{\"intValue\":7}";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenUsingCustomDeserializer_whenFieldNotMatchesCriteria_thenIgnored() {
        final SourceClass sourceObject = new SourceClass(-1, "minus 1");
        final GsonBuilder gsonBuildr = new GsonBuilder();
        gsonBuildr.registerTypeAdapter(SourceClass.class, new IgnoringFieldsNotMatchingCriteriaSerializer());
        final Gson gson = gsonBuildr.create();
        final Type sourceObjectType = new TypeToken<SourceClass>() {
        }.getType();
        final String jsonString = gson.toJson(sourceObject, sourceObjectType);

        final String expectedResult = "{\"stringValue\":\"minus 1\"}";
        assertEquals(expectedResult, jsonString);
    }

    @Test
    public void givenDate_whenSerializing_thenCorrect() {
        Date sourceDate = new DateTime().withYear(2000).withMonthOfYear(1).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();

        final Gson gson = new Gson();
        Type sourceDateType = new TypeToken<Date>() {
        }.getType();
        String jsonDate = gson.toJson(sourceDate, sourceDateType);

        System.out.println("jsonDate:\n" + jsonDate);
        String expectedResult = "\"Jan 1, 2000 12:00:00 AM\"";
        assertEquals(expectedResult, jsonDate);
    }

}