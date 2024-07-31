package com.baeldung.gson.deserialization.test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.baeldung.gson.deserialization.Foo;
import com.baeldung.gson.deserialization.FooDeserializerFromJsonWithDifferentFields;
import com.baeldung.gson.deserialization.FooInstanceCreator;
import com.baeldung.gson.deserialization.FooWithInner;
import com.baeldung.gson.deserialization.GenericFoo;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonDeserializationUnitTest {

    // tests - single element

    @Test
    public final void whenDeserializingToSimpleObject_thenCorrect() {
        final String json = "{\"intValue\":1,\"stringValue\":\"one\"}";

        final Foo targetObject = new Gson().fromJson(json, Foo.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
    }

    @Test
    public final void givenJsonHasExtraValues_whenDeserializing_thenCorrect() {
        final String json = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        final Foo targetObject = new Gson().fromJson(json, Foo.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
    }

    @Test
    public final void givenJsonHasNonMatchingFields_whenDeserializingWithCustomDeserializer_thenCorrect() {
        final String json = "{\"valueInt\":7,\"valueString\":\"seven\"}";

        final GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(Foo.class, new FooDeserializerFromJsonWithDifferentFields());
        final Foo targetObject = gsonBldr.create().fromJson(json, Foo.class);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    @Test
    public final void whenDeserializingToGenericObject_thenCorrect() {
        final Type typeToken = new TypeToken<GenericFoo<Integer>>() {
        }.getType();
        final String json = "{\"theValue\":1}";

        final GenericFoo<Integer> targetObject = new Gson().fromJson(json, typeToken);

        assertEquals(targetObject.theValue, Integer.valueOf(1));
    }

    // tests - multiple elements

    @Test
    public final void givenJsonArrayOfFoos_whenDeserializingToArray_thenCorrect() {
        final String json = "[{\"intValue\":1,\"stringValue\":\"one\"}," + "{\"intValue\":2,\"stringValue\":\"two\"}]";
        final Foo[] targetArray = new GsonBuilder().create().fromJson(json, Foo[].class);

        assertThat(Lists.newArrayList(targetArray), hasItem(new Foo(1, "one")));
        assertThat(Lists.newArrayList(targetArray), hasItem(new Foo(2, "two")));
        assertThat(Lists.newArrayList(targetArray), not(hasItem(new Foo(1, "two"))));
    }

    @Test
    public final void givenJsonArrayOfFoos_whenDeserializingCollection_thenCorrect() {
        final String json = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        final Type targetClassType = new TypeToken<ArrayList<Foo>>() {
        }.getType();

        final Collection<Foo> targetCollection = new Gson().fromJson(json, targetClassType);
        assertThat(targetCollection, instanceOf(ArrayList.class));
    }

    //

    @Test
    public void whenDeserializingJsonIntoElements_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        final JsonElement jElement = JsonParser.parseString(jsonSourceObject);
        final JsonObject jObject = jElement.getAsJsonObject();
        final int intValue = jObject.get("valueInt").getAsInt();
        final String stringValue = jObject.get("valueString").getAsString();

        final Foo targetObject = new Foo(intValue, stringValue);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    // new examples

    @Test
    public void whenDeserializingToNestedObjects_thenCorrect() {
        final String json = "{\"intValue\":1,\"stringValue\":\"one\",\"innerFoo\":{\"name\":\"inner\"}}";

        final FooWithInner targetObject = new Gson().fromJson(json, FooWithInner.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
        assertEquals(targetObject.innerFoo.name, "inner");
    }

    @Test
    public void whenDeserializingUsingInstanceCreator_thenCorrect() {
        final String json = "{\"intValue\":1}";

        final GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(Foo.class, new FooInstanceCreator());
        final Foo targetObject = gsonBldr.create().fromJson(json, Foo.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "sample");
    }

}
