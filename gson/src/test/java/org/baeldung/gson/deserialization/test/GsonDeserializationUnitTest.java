package org.baeldung.gson.deserialization.test;

import org.baeldung.gson.deserialization.GenericSourceClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonDeserializationUnitTest {

    private Gson gson;

    @Before
    public final void before() {
        gson = new Gson();
    }

    // tests

    @Test
    public void givenUsingGson_whenDeserializingGeneric_thenCorrect() {
        final java.lang.reflect.Type genericSourceClassType = new TypeToken<GenericSourceClass>() {
        }.getType();
        final GenericSourceClass sourceObject = new GenericSourceClass(1);
        final String serializedSourceObject = gson.toJson(sourceObject, genericSourceClassType);

        final GenericSourceClass targetObject = gson.fromJson(serializedSourceObject, genericSourceClassType);

        System.out.println(targetObject);
    }

}
