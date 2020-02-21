package com.baeldung.moshi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.junit.Test;

public class ArrayUnitTest {
    @Test
    public void whenSerializingList_thenJsonArrayProduced() {
        Moshi moshi = new Moshi.Builder()
          .build();
        Type type = Types.newParameterizedType(List.class, String.class);
        JsonAdapter<List<String>> jsonAdapter = moshi.adapter(type);

        String json = jsonAdapter.toJson(Arrays.asList("One", "Two", "Three"));
        System.out.println(json);
    }

    @Test
    public void whenDeserializingJsonArray_thenListProduced() throws IOException {
        Moshi moshi = new Moshi.Builder()
          .build();
        Type type = Types.newParameterizedType(List.class, String.class);
        JsonAdapter<List<String>> jsonAdapter = moshi.adapter(type);

        String json = "[\"One\",\"Two\",\"Three\"]";
        List<String> result = jsonAdapter.fromJson(json);
        System.out.println(result);
    }
}
