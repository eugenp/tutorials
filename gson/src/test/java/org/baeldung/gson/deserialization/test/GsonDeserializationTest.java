package org.baeldung.gson.deserialization.test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.baeldung.gson.deserialization.GenericTargetClass;
import org.baeldung.gson.deserialization.SourceClass;
import org.baeldung.gson.deserialization.SourceClassDeserializer;
import org.baeldung.gson.deserialization.TargetClass;
import org.baeldung.gson.deserialization.TargetClassDeserializer;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonDeserializationTest {

    @Test
    public void givenJsonHasDissimilarFieldNamesButGsonMapsRight_whenUsingCustomDeserializer_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        final GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(TargetClass.class, new TargetClassDeserializer());
        final Gson gson = gsonBldr.create();
        final TargetClass targetObject = gson.fromJson(jsonSourceObject, TargetClass.class);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    @Test
    public void givenJsonWithArray_whenUsingGsonCustomDeserializer_thenMapsToArrayList() {
        // It is necessary to override the equals() method in SourceClass
        final String jsonSourceObject = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        final GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeHierarchyAdapter(SourceClass[].class, new SourceClassDeserializer());
        final Gson gson = gsonBldr.create();

        final List<SourceClass> targetList = Arrays.asList(gson.fromJson(jsonSourceObject, SourceClass[].class));

        assertEquals(new SourceClass(1, "one"), targetList.get(0));
    }

    @Test
    public void givenJsonHasDissimilarFieldNamesButGsonMapsRight_whenDeserializingManualy_thenCorrect() {
        final String jsonSourceObject = "{\"valueInt\":7,\"valueString\":\"seven\"}";
        final JsonParser jParser = new JsonParser();
        final JsonElement jElement = jParser.parse(jsonSourceObject);
        final JsonObject jObject = jElement.getAsJsonObject();
        final int intValue = jObject.get("valueInt").getAsInt();
        final String stringValue = jObject.get("valueString").getAsString();

        final TargetClass targetObject = new TargetClass(intValue, stringValue);

        assertEquals(targetObject.intValue, 7);
        assertEquals(targetObject.stringValue, "seven");
    }

    @Test
    public void givenJsonHasExtraValuesButGsonIsIgnoringExtras_whenDeserializing_thenCorrect() {
        final String serializedSourceObject = "{\"intValue\":1,\"stringValue\":\"one\",\"extraString\":\"two\",\"extraFloat\":2.2}";
        final TargetClass targetObject = new Gson().fromJson(serializedSourceObject, TargetClass.class);

        assertEquals(targetObject.intValue, 1);
        assertEquals(targetObject.stringValue, "one");
    }

    @Test
    public void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        final Type genericTargetClassType = new TypeToken<GenericTargetClass<Integer>>() {
        }.getType();
        final String serializedSourceObject = "{\"intField\":1}";

        final GenericTargetClass<Integer> targetObject = new Gson().fromJson(serializedSourceObject, genericTargetClassType);

        assertEquals(targetObject.intField, new Integer(1));
    }

    @Test
    public void givenUsingGson_whenDeserializingCollection_thenCorrect() {
        final String serializedSourceCollection = "[{\"intValue\":1,\"stringValue\":\"one\"},{\"intValue\":2,\"stringValue\":\"two\"}]";
        final Type targetClassType = new TypeToken<ArrayList<TargetClass>>() {
        }.getType();

        final Collection<TargetClass> targetCollection = new Gson().fromJson(serializedSourceCollection, targetClassType);
        assertThat(targetCollection, instanceOf(ArrayList.class));
    }
}
