package org.baeldung.gson.deserialization.test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.baeldung.gson.deserialization.Foo;
import org.baeldung.gson.deserialization.FooDeserializerFromJsonWithDifferentFields;
import org.baeldung.gson.deserialization.GenericFoo;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonDeserializationTest {

    // tests - single element

    @Test
    public final void givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        final String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        final Foo targetObject = new Gson().fromJson(serializedSourceObject, Foo.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
    }

    @Test
    public final void givenJsonHasNonMatchingFieldNames_whenDeserializingWithCustomDeserializer_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";

        final GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(Foo.class, new FooDeserializerFromJsonWithDifferentFields());
        final Foo targetObject = gsonBldr.create().fromJson(jsonSourceObject, Foo.class);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    @Test
    public final void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        final Type genericTargetClassType = new TypeToken<GenericFoo<Integer>>() {
        }.getType();
        final String serializedSourceObject = "{\"intField\":1}";

        final GenericFoo<Integer> targetObject = new Gson().fromJson(serializedSourceObject, genericTargetClassType);

        assertEquals(targetObject.intField, new Integer(1));
    }

    // tests - multiple elements

    @Test
    public final void givenJsonArrayOfFoos_whenDeserializingToList_thenCorrect() {
        final String jsonSourceObject = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        final Foo[] objectsAsArray = new GsonBuilder().create().fromJson(jsonSourceObject, Foo[].class);
        final List<Foo> targetList = Arrays.asList(objectsAsArray);

        assertThat(targetList, hasItem(new Foo(1, "one")));
        assertThat(targetList, hasItem(new Foo(2, "two")));
        assertThat(targetList, not(hasItem(new Foo(1, "two"))));
    }

    @Test
    public final void givenUsingGson_whenDeserializingCollection_thenCorrect() {
        final String serializedSourceCollection = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        final Type targetClassType = new TypeToken<ArrayList<Foo>>() {
        }.getType();

        final Collection<Foo> targetCollection = new Gson().fromJson(serializedSourceCollection, targetClassType);
        assertThat(targetCollection, instanceOf(ArrayList.class));
    }

    //

    @Test
    public void whenDeserializingJsonIntoElements_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        final JsonParser jParser = new JsonParser();
        final JsonElement jElement = jParser.parse(jsonSourceObject);
        final JsonObject jObject = jElement.getAsJsonObject();
        final int intValue = jObject.get("valueInt").getAsInt();
        final String stringValue = jObject.get("valueString").getAsString();

        final Foo targetObject = new Foo(intValue, stringValue);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

}
